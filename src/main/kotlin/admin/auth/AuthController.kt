package admin.auth

import io.javalin.http.BadRequestResponse
import io.javalin.http.Context
import io.javalin.http.util.RateLimit
import io.javalin.http.util.RateLimiter
import java.util.concurrent.TimeUnit

data class Credentials(val userId: String, val password: String)

object AuthController {

    fun signUp(ctx: Context) {
        RateLimit(ctx).requestPerTimeUnit(20, TimeUnit.HOURS)
        val (userId, password) = ctx.bodyValidator<Credentials>()
            .check({ it.userId.trim().length >= 6 }, "User ID must be at least 6 characters")
            .check({ it.password.trim().length >= 6 }, "Password must be at least 6 characters")
            .get()
        if (AccountService.findById(userId) != null) { // user already exists
            throw BadRequestResponse("User ID '$userId' is taken")
        }
        AccountService.create(id = userId, password = password)
        ctx.userInfo = AccountService.findById(userId)!!.let { UserInfo(it.id, it.role) }
        ctx.status(201)
    }

    fun signIn(ctx: Context) {
        RateLimit(ctx).requestPerTimeUnit(20, TimeUnit.HOURS)
        val (userId, password) = ctx.bodyValidator<Credentials>()
            .check({ it.userId.isNotBlank() && it.password.isNotBlank() }, "Both fields are required")
            .get()
        val user = AccountService.findByIdAndPassword(userId, password) ?: throw BadRequestResponse("Incorrect user ID or password")
        ctx.userInfo = UserInfo(user.id, user.role)
    }

    fun signOut(ctx: Context) {
        ctx.userInfo = null
    }

}
