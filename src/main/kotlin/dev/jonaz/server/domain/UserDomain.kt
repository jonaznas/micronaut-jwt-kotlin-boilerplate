package dev.jonaz.server.domain

import dev.jonaz.server.util.exposed.WriteSchema
import org.jetbrains.exposed.sql.Table

@WriteSchema
object UserDomain : Table("user_list") {
    val id = integer("id").autoIncrement()

    val name = varchar("name", 256)
    val password = text("password")
    val roles = text("roles")

    override val primaryKey = PrimaryKey(id, name = "username")
}
