package dev.jonaz.server.components.user

import dev.jonaz.server.domain.UserDomain
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

object UserRegistration {
    private val domain = UserDomain

    fun validate(username: String, password: String) = when {
        // Check allowed characters in username
        username !in Regex("[a-zA-Z0-9 ]*") -> Pair(false, "The username can only be alphanumeric")

        // Check password security
        password in Regex("[a-zA-Z0-9 ]*")  -> Pair(false, "The password must have a special character")

        // Check username length
        username.length > 16                -> Pair(false, "The username is too long")
        username.length < 4                 -> Pair(false, "The username is too short")

        // Check password length
        password.length > 200               -> Pair(false, "The password is too long")
        password.length < 6                 -> Pair(false, "The password is too short")

        else                                -> Pair(true, "")
    }

    fun usernamePasswordRegistration(username: String, password: String): Pair<Boolean, String> {
        validate(username, password).let {
            if (it.first.not())
                return it
        }

        UserAccount.get(username).let {
            if (it.isNotEmpty())
                return Pair(false, "The username is already taken")
        }

        insert(username, password)

        return Pair(true, "")
    }

    private fun insert(username: String, password: String) = transaction {
        domain.insert {
            it[domain.name] = username
            it[domain.password] = password // TODO bcrypt
        }
    }

    private operator fun Regex.contains(chars: CharSequence): Boolean = this.matches(chars)
}
