package kat.example

import io.javalin.http.Context
import kat.auth.userInfo

data class CreateRequest(val text: String)

object ExampleController {

    fun create(ctx: Context) {
        val createRequest = ctx.bodyValidator<CreateRequest>()
            .check({ it.text.trim().length >= 6 }, "Text must be at least 6 characters")
            .get()
        ExampleService.create(createRequest.text, ctx.userInfo!!.id)
        ctx.status(201)
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
