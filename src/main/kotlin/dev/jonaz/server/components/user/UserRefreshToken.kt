package dev.jonaz.server.components.user

import dev.jonaz.server.components.user.model.RefreshTokenModel
import dev.jonaz.server.domain.UserRefreshTokenDomain
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

object UserRefreshToken {

    private val domain = UserRefreshTokenDomain

    fun get(refreshToken: String) = transaction {
        domain.select { domain.refreshToken eq refreshToken }.map {
            RefreshTokenModel(it[domain.refreshToken], it[domain.user], it[domain.revoked])
        }
    }
}
