package admin.account

import admin.auth.AccountService
import io.javalin.http.Context

object AccountController {

    fun getAll(ctx: Context) {
        ctx.json(AccountService.list())
    }

    fun update(ctx: Context) {
        TODO("Not yet implemented")
    }

    fun delete(ctx: Context) {
        TODO("Not yet implemented")
    }

}
