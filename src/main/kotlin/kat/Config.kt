package kat

import java.lang.System.getenv

object Config {
    val port = getenv("PORT")?.toInt() ?: 8080
    val allowFakeLogin = getenv("ALLOW_FAKE_LOGIN")?.toBoolean() ?: true
}
