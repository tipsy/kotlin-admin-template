package kat

import io.javalin.Javalin
import kat.auth.Role
import kat.util.resetDatabase
import kong.unirest.Unirest
import kong.unirest.UnirestInstance
import org.junit.AfterClass
import org.junit.BeforeClass

open class BaseFunctionalTest {
    companion object {
        lateinit var app: Javalin
        lateinit var http: UnirestInstance

        @JvmStatic
        @BeforeClass
        fun setup() {
            app = createApp().apply { config.showJavalinBanner = false }.start(0)
            http = Unirest.spawnInstance().apply { config().defaultBaseUrl("http://localhost:${app.port()}") }
        }

        @JvmStatic
        @AfterClass
        fun tearDown() {
            app.stop()
        }
    }

    fun runTest(role: Role, resetDb: Boolean = false, test: Runnable) {
        if (role == Role.UNAUTHENTICATED) {
            http.post("/api/auth/sign-out").asString()
        } else {
            http.get("/").queryString("user", "fake-user").queryString("role", role).asString() // fake login
        }
        test.run()
        if (resetDb) {
            resetDatabase()
        }
    }
}
