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

}
