package kat.view

import io.github.bonigarcia.wdm.WebDriverManager
import io.javalin.Javalin
import kat.auth.Role
import kat.createApp
import kat.util.initDatabaseIfEmpty
import kat.util.resetDatabase
import org.junit.AfterClass
import org.junit.BeforeClass
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions

open class BaseBrowserTest {
    companion object {
        lateinit var app: Javalin
        lateinit var origin: String
        lateinit var chrome: ChromeDriver

        @JvmStatic
        @BeforeClass
        fun setup() {
            app = createApp().apply { _conf.showJavalinBanner = false }.start(0).also { initDatabaseIfEmpty() }
            origin = "http://localhost:${app.port()}"
            WebDriverManager.chromedriver().setup()
            chrome = ChromeDriver(ChromeOptions().apply {
                addArguments("--headless")
                addArguments("--disable-gpu")
            })
        }

        @JvmStatic
        @AfterClass
        fun tearDown() {
            chrome.quit()
        }
    }

    fun runTest(role: Role, resetDb: Boolean = false, test: Runnable) {
        if (role == Role.UNAUTHENTICATED) {
            chrome.get("$origin/?clear-login=true")
        } else {
            chrome.get("$origin/?user=fake-user&role=${role}")
        }
        test.run()
        if (resetDb) {
            resetDatabase()
        }
    }

}
