package kat.auth

import kat.Database
import org.jdbi.v3.core.kotlin.bindKotlin
import org.mindrot.jbcrypt.BCrypt

data class AccountCreate(val id: String, val password: String, val role: Role)
data class AccountView(val id: String, val role: Role)

object AccountService {

    fun create(id: String, password: String, role: Role = Role.USER) = Database.useHandle<Exception> { handle ->
        handle.createUpdate("INSERT INTO account (id, password, role) VALUES (:id, :password, :role)")
            .bindKotlin(AccountCreate(id, BCrypt.hashpw(password, BCrypt.gensalt()), role))
            .execute()
    }

    fun list(): List<AccountView> = Database.withHandle<List<AccountView>, Exception> { handle ->
        handle.createQuery("SELECT * FROM account").mapTo(AccountView::class.java).list()
    }

    fun findById(id: String): AccountCreate? = Database.withHandle<AccountCreate?, Exception> { handle ->
        handle.createQuery("SELECT * FROM account WHERE id=:id")
            .bind("id", id)
            .mapTo(AccountCreate::class.java)
            .firstOrNull()
    }

    fun findByIdAndPassword(id: String, password: String): AccountCreate? {
        val user = findById(id) ?: return null
        return if (BCrypt.checkpw(password, user.password)) user else null
    }

    fun updateById(id: String, role: Role): Boolean = Database.withHandle<Boolean, Exception> { handle ->
        val rowsModified = handle.createUpdate("UPDATE account SET role=:role WHERE id=:id")
            .bind("id", id)
            .bind("role", role)
            .execute()
        return@withHandle rowsModified > 0
    }

    fun deleteById(id: String): Boolean = Database.withHandle<Boolean, Exception> { handle ->
        val rowsModified = handle.createUpdate("DELETE FROM account WHERE id=:id")
            .bind("id", id)
            .execute()
        return@withHandle rowsModified > 0
    }

}
