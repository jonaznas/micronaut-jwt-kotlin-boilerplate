package dev.jonaz.server.tables

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.`java-time`.datetime
import java.time.LocalDateTime

object UserRefreshTokenTable : Table("user_refresh_tokens") {
    val id = integer("id").autoIncrement()

    val user = integer("user")
    val refreshToken = text("refresh_token")
    val revoked = bool("revoked")
    val created = datetime("created").default(LocalDateTime.now())

    override val primaryKey = PrimaryKey(id, name = "id")
}
