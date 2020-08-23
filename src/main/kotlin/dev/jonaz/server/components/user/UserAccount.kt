package dev.jonaz.server.components.user

import dev.jonaz.server.components.user.model.UserModel
import dev.jonaz.server.domain.UserDomain
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class UserAccount {

    companion object {
        private val table = UserDomain

        fun get(name: String) = transaction {
            table.select { table.name eq name }.map {
                UserModel(it[table.id], it[table.name], it[table.password])
            }
        }

        fun get(id: Int) = transaction {
            table.select { table.id eq id }.map {
                UserModel(it[table.id], it[table.name], it[table.password])
            }
        }
    }
}
