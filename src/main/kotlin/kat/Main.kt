package kat

import kat.account.AccountController
import kat.auth.AccessManager
import kat.auth.AuthController
import kat.auth.Role.*
import kat.auth.Session
import kat.auth.userInfo
import kat.example.ExampleController
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*
import io.javalin.core.security.SecurityUtil.roles
import io.javalin.plugin.rendering.vue.JavalinVue
import io.javalin.plugin.rendering.vue.VueComponent

fun main() {

    val app = Javalin.create {
        it.addStaticFiles("/public")
        it.enableWebjars()
        it.sessionHandler { Session.fileSessionHandler() }
        it.accessManager(AccessManager::manage)
        JavalinVue.stateFunction = { ctx -> mapOf("userInfo" to ctx.userInfo) }
        JavalinVue.optimizeDependencies = true
    }.start(Config.port)

    app.routes { // view routes
        get("/", VueComponent("home-page"), roles(USER, ADMIN))
        get("/examples", VueComponent("examples-page"), roles(USER, ADMIN))
        get("/accounts", VueComponent("accounts-page"), roles(ADMIN))
        get("/sign-in", VueComponent("sign-in-page"), roles(UNAUTHENTICATED))
    }

    app.routes { // api routes
        path("api") {
            path("auth") {
                post("sign-up", AuthController::signUp, roles(UNAUTHENTICATED))
                post("sign-in", AuthController::signIn, roles(UNAUTHENTICATED))
                post("sign-out", AuthController::signOut, roles(UNAUTHENTICATED))
            }
            path("examples") {
                get(ExampleController::getAll, roles(USER, ADMIN))
                post(ExampleController::create, roles(USER, ADMIN))
                path(":example-id") {
                    delete(ExampleController::delete, roles(USER, ADMIN))
                }
            }
            path("accounts") {
                get(AccountController::getAll, roles(ADMIN))
                path(":account-id") {
                    patch(AccountController::update, roles(ADMIN))
                    delete(AccountController::delete, roles(ADMIN))
                }
            }
        }
    }

    app.apply { // error pages
        error(401, "html", VueComponent("unauthorized-page"))
        error(404, "html", VueComponent("not-found-page"))
    }

}
