package dev.jonaz.server.tables

import org.jetbrains.exposed.sql.Table

object UserTable : Table("user_list") {
    val id = integer("id").autoIncrement()

    val name = varchar("name", 256)
    val password = text("password")

    override val primaryKey = PrimaryKey(id, name = "username")
}
