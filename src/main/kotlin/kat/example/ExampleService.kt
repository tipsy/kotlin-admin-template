package kat.example

import kat.Database
import org.jdbi.v3.core.kotlin.bindKotlin

object ExampleService {

    data class ExampleCreate(val text: String, val createdBy: String)
    data class ExampleView(val id: Int, val text: String, val createdBy: String)

    fun create(text: String, createdBy: String) = Database.useHandle<Exception> { handle ->
        handle.createUpdate("INSERT INTO example (text, created_by) VALUES (:text, :createdBy)")
            .bindKotlin(ExampleCreate(text, createdBy))
            .execute()
    }

    fun list(): List<ExampleView> = Database.withHandle<List<ExampleView>, Exception> { handle ->
        handle.createQuery("SELECT * FROM example").mapTo(ExampleView::class.java).list().reversed()
    }

    fun deleteByIdAndOwner(id: Int, owner: String): Boolean = Database.withHandle<Boolean, Exception> { handle ->
        val rowsModified = handle.createUpdate("DELETE FROM example WHERE id=:id AND created_by=:owner")
            .bind("id", id)
            .bind("owner", owner)
            .execute()
        return@withHandle rowsModified > 0
    }

}
