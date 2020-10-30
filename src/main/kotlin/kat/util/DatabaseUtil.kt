package kat.util

import kat.Database
import kat.auth.AccountService
import kat.auth.Role
import kat.example.ExampleService

// database-setup/reset
fun main() {
    resetDatabase(logStatus = true)
}

fun resetDatabase(logStatus: Boolean = false) {
    if (logStatus) println("Setting up database...")

    Database.useHandle<Exception> { handle ->
        // users
        handle.execute("DROP TABLE IF EXISTS account")
        handle.execute("CREATE TABLE account (id STRING PRIMARY KEY, password STRING, role STRING)")
        // examples
        handle.execute("DROP TABLE IF EXISTS example")
        handle.execute("CREATE TABLE example (id INTEGER PRIMARY KEY AUTOINCREMENT, text STRING, created_by STRING)")
    }

    val adminUser = "admin@kat.kat"
    AccountService.create(id = adminUser, password = "password", role = Role.ADMIN)
    ExampleService.create(text = "One of a number of things, or a part of something, taken to show the character of the whole", createdBy = adminUser)
    ExampleService.create(text = "A pattern or model, as of something to be imitated or avoided", createdBy = adminUser)
    ExampleService.create(text = "An instance serving for illustration; specimen", createdBy = adminUser)
    ExampleService.create(text = "An instance illustrating a rule or method, as a mathematical problem proposed for solution", createdBy = adminUser)
    ExampleService.create(text = "An instance, especially of punishment, serving as a warning to others:", createdBy = adminUser)

    if (logStatus) println("Database setup complete!")
}