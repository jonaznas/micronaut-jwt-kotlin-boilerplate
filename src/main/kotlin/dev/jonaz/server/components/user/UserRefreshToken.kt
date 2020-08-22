package dev.jonaz.server.components.user

import dev.jonaz.server.components.user.model.RefreshTokenModel
import dev.jonaz.server.tables.UserRefreshTokenTable
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class UserRefreshToken {

    companion object {
        private val table = UserRefreshTokenTable

        fun get(refreshToken: String) = transaction {
            table.select { table.refreshToken eq refreshToken }.map {
                RefreshTokenModel(it[table.refreshToken], it[table.user], it[table.revoked])
            }
        }
    }
}
