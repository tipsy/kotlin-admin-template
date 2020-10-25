//package io.javalin.example.kotlin
//
//import io.github.bonigarcia.wdm.WebDriverManager
//import org.assertj.core.api.Assertions.assertThat
//import org.junit.Test
//import org.openqa.selenium.WebDriver
//import org.openqa.selenium.chrome.ChromeDriver
//import org.openqa.selenium.chrome.ChromeOptions
//
//class EndToEndTest {
//
//    private val app = JavalinApp() // inject any dependencies you might have
//
//    @Test
//    fun `UI contains correct heading`() {
//        app.start(1234)
//        WebDriverManager.chromedriver().setup()
//        val driver: WebDriver = ChromeDriver(ChromeOptions().apply {
//            addArguments("--headless")
//            addArguments("--disable-gpu")
//        })
//        driver.get("http://localhost:1234/ui")
//        assertThat(driver.pageSource).contains("<h1>User UI</h1>")
//        driver.quit()
//        app.stop()
//    }
//
//}
