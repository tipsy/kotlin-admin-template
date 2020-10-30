package kat.browser

import io.github.bonigarcia.wdm.WebDriverManager
import io.javalin.Javalin
import kat.api.BaseApiTest
import kat.auth.Role
import kat.createApp
import kat.util.resetDatabase
import org.junit.AfterClass
import org.junit.Before
import org.junit.BeforeClass
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions

open class BaseBrowserTest  {
    companion object {
        lateinit var app: Javalin
        lateinit var origin: String
        lateinit var chrome: ChromeDriver

        @JvmStatic
        @BeforeClass
        fun setup() {
            app = createApp().apply { config.showJavalinBanner = false }.start(0)
            origin = "http://localhost:${app.port()}"
            WebDriverManager.chromedriver().setup()
        }

        @JvmStatic
        @AfterClass
        fun tearDown() {
            chrome.quit()
        }
    }

    @Before
    fun beforeEach() {
        chrome = ChromeDriver(ChromeOptions().apply {
            addArguments("--headless")
            addArguments("--disable-gpu")
        })
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
