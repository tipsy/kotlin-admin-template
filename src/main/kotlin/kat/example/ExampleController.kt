package kat.example

import kat.auth.userInfo
import io.javalin.http.Context

object ExampleController {

    fun create(ctx: Context) {
        ExampleService.create(ctx.body(), ctx.userInfo!!.id)
    }

    fun getAll(ctx: Context) {
        ctx.json(ExampleService.list())
    }

    fun delete(ctx: Context) {
        val exampleId = ctx.pathParam<Int>("example-id").get()
        val deleted = ExampleService.deleteByIdAndOwner(exampleId, ctx.userInfo!!.id)
        ctx.status(if (deleted) 204 else 404)
    }

}
