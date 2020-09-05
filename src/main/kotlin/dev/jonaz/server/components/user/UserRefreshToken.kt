package dev.jonaz.server.components.user

import dev.jonaz.server.components.user.model.RefreshTokenModel
import dev.jonaz.server.domain.UserRefreshTokenDomain
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRefreshToken @Inject constructor(
        private val domain: UserRefreshTokenDomain
) {

    fun get(refreshToken: String) = transaction {
        domain.select { domain.refreshToken eq refreshToken }.map {
            RefreshTokenModel(it[domain.refreshToken], it[domain.user], it[domain.revoked])
        }
    }

    fun save(payload: String, user: Int) = transaction {
        domain.insert {
            it[domain.refreshToken] = payload
            it[domain.user] = user
            it[domain.revoked] = false
        }
    }
}
