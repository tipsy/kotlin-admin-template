package kat.api

import io.javalin.Javalin
import kat.auth.Role
import kat.createApp
import kat.util.initDatabaseIfEmpty
import kat.util.resetDatabase
import kong.unirest.Unirest
import kong.unirest.UnirestInstance
import org.junit.AfterClass
import org.junit.BeforeClass

open class BaseApiTest {
    companion object {
        lateinit var app: Javalin
        lateinit var http: UnirestInstance

        @JvmStatic
        @BeforeClass
        fun beforeClass() {
            app = createApp().apply { config.showJavalinBanner = false }.start(0).also { initDatabaseIfEmpty() }
            http = Unirest.spawnInstance().apply { config().defaultBaseUrl("http://localhost:${app.port()}") }
        }

        @JvmStatic
        @AfterClass
        fun afterClass() {
            app.stop()
            http.shutDown()
        }
    }

    fun runTest(role: Role, resetDb: Boolean = false, test: Runnable) {
        if (role == Role.UNAUTHENTICATED) {
            http.get("/?clear-login=true").asString()
        } else {
            http.get("/?user=fake-user&role=${role}").asString() // fake login
        }
        test.run()
        if (resetDb) {
            resetDatabase()
        }
    }
}
