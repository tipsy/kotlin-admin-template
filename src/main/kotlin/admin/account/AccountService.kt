package admin.auth

import admin.Database
import org.jdbi.v3.core.kotlin.bindKotlin
import org.mindrot.jbcrypt.BCrypt

data class Account(val id: String, val password: String, val role: Role)
data class AccountView(val id: String, val role: Role)

object AccountService {

    fun create(id: String, password: String, role: Role = Role.USER) = Database.useHandle<Exception> { handle ->
        handle.createUpdate("INSERT INTO account (id, password, role) VALUES (:id, :password, :role)")
            .bindKotlin(Account(id, BCrypt.hashpw(password, BCrypt.gensalt()), role))
            .execute()
    }

    fun list(): List<AccountView> = Database.withHandle<List<AccountView>, Exception> { handle ->
        handle.createQuery("SELECT * FROM account").mapTo(AccountView::class.java).list()
    }

    fun findById(id: String): Account? = Database.withHandle<Account?, Exception> { handle ->
        handle.createQuery("SELECT * FROM account WHERE id=:id")
            .bind("id", id)
            .mapTo(Account::class.java)
            .firstOrNull()
    }

    fun findByIdAndPassword(id: String, password: String): Account? {
        val user = findById(id) ?: return null
        return if (BCrypt.checkpw(password, user.password)) user else null
    }

    fun updateById(id: String, role: Role) {

    }

    fun deleteById(id: String) {

    }

}
