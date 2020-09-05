package dev.jonaz.server.components.user

import dev.jonaz.server.components.user.model.UserModel
import dev.jonaz.server.domain.UserDomain
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserAccount @Inject constructor(
        private val domain: UserDomain
) {

    fun get(name: String) = transaction {
        domain.select { domain.name eq name }.map {
            UserModel(it[domain.id], it[domain.name], it[domain.password], decodeRoles(it))
        }
    }

    fun get(id: Int) = transaction {
        domain.select { domain.id eq id }.map {
            UserModel(it[domain.id], it[domain.name], it[domain.password], decodeRoles(it))
        }
    }

    private fun decodeRoles(row: ResultRow): List<String> {
        return Json.decodeFromString(row[domain.roles])
    }
}
