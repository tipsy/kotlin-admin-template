package kat

import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.KotlinPlugin

val Database = Jdbi.create("jdbc:sqlite:kotlin-admin-template.db")
    .installPlugin(KotlinPlugin())
