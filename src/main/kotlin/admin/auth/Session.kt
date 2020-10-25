package admin.auth

import org.eclipse.jetty.server.session.*
import java.io.File

object Session {

    // You can learn more about session handling here: https://javalin.io/tutorials/jetty-session-handling

    // session are stored in a file - this works well on localhost, but consider changing if you're deploying your app
    fun fileSessionHandler() = SessionHandler().apply {
        httpOnly = true
        sessionCache = DefaultSessionCache(this).apply {
            sessionDataStore = FileSessionDataStore().apply {
                val baseDir = File(System.getProperty("java.io.tmpdir"))
                this.storeDir = File(baseDir, "kat-session-store").apply { mkdir() }
            }
        }
    }

    // use this if you need a session database
    fun sqlSessionHandler(driver: String, url: String) = SessionHandler().apply {
        sessionCache = DefaultSessionCache(this).apply { // use a NullSessionCache if you don't have sticky sessions
            sessionDataStore = JDBCSessionDataStoreFactory().apply {
                setDatabaseAdaptor(DatabaseAdaptor().apply {
                    setDriverInfo(driver, url)
                })
            }.getSessionDataStore(sessionHandler)
        }
        httpOnly = true
        // make additional changes to your SessionHandler here
    }

}
