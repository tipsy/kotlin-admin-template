package kat.api

import kat.Config
import kat.auth.AccountService
import kat.auth.Role
import kat.util.resetDatabase
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

// You have to set Config.useFakeLogin=true for most of the tests
class TestEndpoints : BaseApiTest() {

    @Test
    fun `POST examples accepts valid example`() = runTest(Role.USER, resetDb = true) {
        val validExample = """{"text": "My example"}"""
        val response = http.post("/api/examples/").body(validExample).asString()
        assertThat(response.status).isEqualTo(201)
    }

    @Test
    fun `POST examples rejects invalid example`() = runTest(Role.USER) {
        val invalidExample = """{"text": "My      "}"""
        val response = http.post("/api/examples/").body(invalidExample).asString()
        assertThat(response.status).isEqualTo(400)
        assertThat(response.body).contains("Text must be at least 6 characters")
    }

    @Test
    fun `GET accounts is not available for USER`() = runTest(Role.USER) {
        val response = http.get("/api/accounts/").asString()
        assertThat(response.status).isEqualTo(401)
    }

    @Test
    fun `GET accounts is available for ADMIN`() = runTest(Role.ADMIN) {
        val response = http.get("/api/accounts/").asString()
        assertThat(response.status).isEqualTo(200)
    }

    @Test
    fun `GET anything is redirected to login for unauthenticated user`() = runTest(Role.UNAUTHENTICATED) {
        val response = http.get("/").asString()
        assertThat(response.body).contains("<sign-in-page></sign-in-page>")
    }

}

class TestEndpoints2 : BaseApiTest() {

    // Warning: this test must not be run in parallel with other tests
    @Test
    fun `GET anything is redirected to login for non-existent user`() {
        resetDatabase()
        // we don't need fake login here
        Config.useFakeLogin = false
        val response = http.get("/sign-in").asString()
        assertThat(response.status).isEqualTo(200)
        val credentials = """{"userId":"FirstUser","password":"password"}"""
        val response2 = http.post("/api/auth/sign-up").body(credentials).asString()
        assertThat(response2.status).isEqualTo(201)
        // HTTP client sends session cookie of existing user
        val response3 = http.get("/").asString()
        assertThat(response3.body).doesNotContain("<sign-in-page></sign-in-page>")
        assertThat(AccountService.deleteById("FirstUser")).isTrue
        assertThat(AccountService.findById("FirstUser")).isNull()
        // HTTP client sends session cookie of non-existent user
        val response4 = http.get("/").asString()
        assertThat(response4.body).contains("<sign-in-page></sign-in-page>")
    }
}
