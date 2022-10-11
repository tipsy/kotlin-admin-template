package kat.auth

import io.javalin.http.Context
import io.javalin.http.Handler
import io.javalin.security.RouteRole
import kat.Config
import java.io.Serializable

enum class Role : RouteRole {
    UNAUTHENTICATED,
    USER,
    ADMIN
}

object AccessManager {
    fun manage(handler: Handler, ctx: Context, permittedRoles: Set<RouteRole>) {
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
    if (Config.useFakeLogin) return doFakeLogin()
    // if it's not a test-related login we refresh the userinfo (but only if it's already set)
    this.userInfo?.let {
        val accountCreate = AccountService.findById(it.id)
        this.userInfo = accountCreate?.let { UserInfo(this.userInfo!!.id, accountCreate.role) }
    }
}

data class UserInfo(val id: String, val role: Role) : Serializable // must be serializable to store in session file/db

var Context.userInfo: UserInfo?
    get() = this.sessionAttribute("USER_INFO")
    set(userInfo) = this.sessionAttribute("USER_INFO", userInfo)

/**
 * This method is used for testing in [src.test.kotlin.kat.api.TestEndpoints]
 * and [src.test.kotlin.kat.view.TestPages].
 */
private fun Context.doFakeLogin() {
    if (this.queryParam("user") != null && this.queryParam("role") != null) {
        this.userInfo = UserInfo(this.queryParam("user")!!, Role.valueOf(this.queryParam("role")!!))
    } else if (this.queryParam("clear-login") != null) {
        this.userInfo = null
    }
}
