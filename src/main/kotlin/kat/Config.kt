package kat

import java.lang.System.getenv

object Config {
    val port = getenv("PORT")?.toInt() ?: 8080
    val useFakeLogin = getenv("USE_FAKE_LOGIN")?.toBoolean() ?: false
}
