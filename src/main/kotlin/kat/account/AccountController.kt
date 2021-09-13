package kat.account

import io.javalin.http.Context
import kat.auth.AccountService
import kat.auth.Role

data class UpdateRequest(val role: Role)

object AccountController {

    fun getAll(ctx: Context) {
        ctx.json(AccountService.list())
    }

    fun update(ctx: Context) {
        val accountId = ctx.pathParam("account-id")
        val updateRequest = ctx.bodyAsClass<UpdateRequest>()
        AccountService.updateById(accountId, updateRequest.role)
    }

    fun delete(ctx: Context) {
        val accountId = ctx.pathParam("account-id")
        val deleted = AccountService.deleteById(accountId)
        ctx.status(if (deleted) 204 else 404)
    }

}
