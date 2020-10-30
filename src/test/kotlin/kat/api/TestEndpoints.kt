package kat.api

import kat.auth.Role
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

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
