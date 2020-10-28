package admin.account

import admin.auth.AccountService
import admin.auth.Role
import io.javalin.http.Context

data class UpdateRequest(val role: Role)

object AccountController {

    fun getAll(ctx: Context) {
        ctx.json(AccountService.list())
    }

    fun update(ctx: Context) {
        val accountId = ctx.pathParam<String>("account-id").get()
        val updateRequest = ctx.body<UpdateRequest>()
        AccountService.updateById(accountId, updateRequest.role)
    }

    fun delete(ctx: Context) {
        val accountId = ctx.pathParam<String>("account-id").get()
        val deleted = AccountService.deleteById(accountId)
        ctx.status(if (deleted) 204 else 404)
    }

}
