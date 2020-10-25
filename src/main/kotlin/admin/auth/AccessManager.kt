package admin.auth

import io.javalin.http.Context
import io.javalin.http.Handler
import java.io.Serializable
import io.javalin.core.security.Role as JavalinRole

enum class Role : JavalinRole {
    UNAUTHENTICATED,
    USER,
    ADMIN
}

object AccessManager {
    fun manage(handler: Handler, ctx: Context, permittedRoles: Set<JavalinRole>) {
        when {
            Role.UNAUTHENTICATED in permittedRoles -> handler.handle(ctx)
            ctx.userInfo == null -> ctx.redirect("/sign-in")
            ctx.userInfo!!.role in permittedRoles -> handler.handle(ctx) // user role fits any onf the roles for the endpoint
            else -> ctx.status(401)
        }
    }
}

data class UserInfo(val id: String, val role: Role) : Serializable // must be serializable to store in session file/db

var Context.userInfo: UserInfo?
    get() = this.sessionAttribute("USER_INFO")
    set(userInfo) = this.sessionAttribute("USER_INFO", userInfo)
