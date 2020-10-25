//package io.javalin.example.kotlin
//
//import io.javalin.plugin.json.JavalinJson
//import kong.unirest.HttpResponse
//import kong.unirest.Unirest
//import org.assertj.core.api.Assertions.assertThat
//import org.junit.Test
//
//class FunctionalTest {
//
//    private val app = JavalinApp() // inject any dependencies you might have
//    private val usersJson = JavalinJson.toJson(UserController.users)
//
//    @Test
//    fun `GET to fetch users returns list of users`() {
//        app.start(1234)
//        val response: HttpResponse<String> = Unirest.get("http://localhost:1234/users").asString()
//        assertThat(response.status).isEqualTo(200)
//        assertThat(response.body).isEqualTo(usersJson)
//        app.stop()
//    }
//
//}
