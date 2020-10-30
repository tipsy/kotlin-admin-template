package kat.auth

import io.javalin.http.Context
import io.javalin.http.Handler
import kat.Config
import java.io.Serializable
import io.javalin.core.security.Role as JavalinRole

enum class Role : JavalinRole {
    UNAUTHENTICATED,
    USER,
    ADMIN
}

object AccessManager {
    fun manage(handler: Handler, ctx: Context, permittedRoles: Set<JavalinRole>) {
        ctx.refreshUserInfo() // make sure role matches database role
        when {
            Role.UNAUTHENTICATED in permittedRoles -> handler.handle(ctx)
            ctx.userInfo == null -> ctx.redirect("/sign-in")
            ctx.userInfo!!.role in permittedRoles -> handler.handle(ctx) // user role fits any onf the roles for the endpoint
            else -> ctx.status(401)
        }
    }
}

private fun Context.refreshUserInfo() {
    if (Config.allowFakeLogin) {
        if (this.queryParam("user") != null && this.queryParam("role") != null) {
            this.userInfo = UserInfo(this.queryParam("user")!!, Role.valueOf(this.queryParam("role")!!))
        }
    } else { // if it's not a fake login we refresh userinfo (but only if it's already set)
        this.userInfo?.let { this.userInfo = UserInfo(it.id, AccountService.findById(it.id)!!.role) }
    }
}

data class UserInfo(val id: String, val role: Role) : Serializable // must be serializable to store in session file/db

var Context.userInfo: UserInfo?
    get() = this.sessionAttribute("USER_INFO")
    set(userInfo) = this.sessionAttribute("USER_INFO", userInfo)
