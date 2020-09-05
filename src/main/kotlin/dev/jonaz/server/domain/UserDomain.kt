package dev.jonaz.server.domain

import dev.jonaz.server.util.exposed.CreateSchema
import org.jetbrains.exposed.sql.Table
import javax.inject.Singleton

@CreateSchema
@Singleton
class UserDomain : Table("user_list") {
    val id = integer("id").autoIncrement()

    val name = varchar("name", 256)
    val password = text("password")
    val roles = text("roles")

    override val primaryKey = PrimaryKey(id, name = "username")
}
