package kat.browser

import kat.auth.Role
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class TestPages : BaseBrowserTest() {

    @Test
    fun `index page contains correct title`() = runTest(Role.USER) {
        chrome.get("$origin/")
        assertThat(chrome.pageSource).contains("What's included?")
    }

    @Test
    fun `examples page contains examples`() = runTest(Role.USER) {
        chrome.get("$origin/examples")
        assertThat(chrome.pageSource).doesNotContain("What's included?")
        assertThat(chrome.pageSource).contains("This page contains a bunch of examples")
    }

    @Test
    fun `accounts page not available to non-admins`() = runTest(Role.USER) {
        chrome.get("$origin/accounts")
        assertThat(chrome.pageSource).doesNotContain("This page shows all the user-accounts")
        assertThat(chrome.pageSource).contains("There's a rumor that the admin credentials")
    }

    @Test
    fun `accounts page available to admins`() = runTest(Role.ADMIN) {
        chrome.get("$origin/accounts")
        assertThat(chrome.pageSource).contains("This page shows all the user-accounts")
        assertThat(chrome.pageSource).doesNotContain("There's a rumor that the admin credentials")
    }

}
