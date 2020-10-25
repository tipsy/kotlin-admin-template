//package io.javalin.example.kotlin
//
//import io.javalin.http.BadRequestResponse
//import io.javalin.http.Context
//import io.mockk.every
//import io.mockk.mockk
//import io.mockk.verify
//import org.junit.Test
//
//class UnitTest {
//
//    private val ctx = mockk<Context>(relaxed = true)
//
//    @Test
//    fun `POST to create users gives 201 for valid username`() {
//        every { ctx.queryParam("username") } returns "Roland"
//        UserController.create(ctx) // the handler we're testing
//        verify { ctx.status(201) }
//    }
//
//    @Test(expected = BadRequestResponse::class)
//    fun `POST to create users throws for invalid username`() {
//        every { ctx.queryParam("username") } returns null
//        UserController.create(ctx) // the handler we're testing
//    }
//
//}
