package kat

import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*
import io.javalin.http.staticfiles.Location
import io.javalin.plugin.rendering.vue.JavalinVue
import io.javalin.plugin.rendering.vue.VueComponent
import kat.account.AccountController
import kat.auth.AccessManager
import kat.auth.AuthController
import kat.auth.Role.*
import kat.auth.Session
import kat.auth.userInfo
import kat.example.ExampleController
import kat.util.initDatabaseIfEmpty

fun main() {
    createApp().start(Config.port).also { initDatabaseIfEmpty() }
}

fun createApp(): Javalin { // this is wrapped in a function to enable spinning up server instances for unit tests

    val app = Javalin.create {
        it.addStaticFiles("/public", Location.CLASSPATH)
        it.enableWebjars()
        it.sessionHandler { Session.fileSessionHandler() }
        it.accessManager(AccessManager::manage)
        JavalinVue.stateFunction = { ctx -> mapOf("userInfo" to ctx.userInfo) }
    }

    app.routes { // view routes
        get("/", VueComponent("home-page"), USER, ADMIN)
        get("/examples", VueComponent("examples-page"), USER, ADMIN)
        get("/accounts", VueComponent("accounts-page"), ADMIN)
        get("/sign-in", VueComponent("sign-in-page"), UNAUTHENTICATED)
    }

    app.routes { // api routes
        path("api") {
            path("auth") {
                post("sign-up", AuthController::signUp, UNAUTHENTICATED)
                post("sign-in", AuthController::signIn, UNAUTHENTICATED)
                post("sign-out", AuthController::signOut, UNAUTHENTICATED)
            }
            path("examples") {
                get(ExampleController::getAll, USER, ADMIN)
                post(ExampleController::create, USER, ADMIN)
                path("{example-id}") {
                    delete(ExampleController::delete, USER, ADMIN)
                }
            }
            path("accounts") {
                get(AccountController::getAll, ADMIN)
                path("{account-id}") {
                    patch(AccountController::update, ADMIN)
                    delete(AccountController::delete, ADMIN)
                }
            }
        }
    }

    app.apply { // error pages
        error(401, "html", VueComponent("unauthorized-page"))
        error(404, "html", VueComponent("not-found-page"))
    }

    return app

}
