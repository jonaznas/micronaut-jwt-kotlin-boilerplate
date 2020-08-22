package dev.jonaz.server.util.exposed

import com.zaxxer.hikari.HikariDataSource
import dev.jonaz.server.tables.UserRefreshTokenTable
import dev.jonaz.server.tables.UserTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

class ExposedClient(
        private val dataSource: HikariDataSource
) {

    fun connect() = Database.connect(dataSource).let {
        this.createSchema()
    }

    private fun createSchema() = transaction {
        SchemaUtils.create(
                UserTable,
                UserRefreshTokenTable
        )
    }
}
