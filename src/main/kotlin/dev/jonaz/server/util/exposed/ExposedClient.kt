package dev.jonaz.server.util.exposed

import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import java.sql.Connection

class ExposedClient(
        private val dataSource: HikariDataSource
) {
    companion object {
        private lateinit var connection: Connection

        fun closeConnection() = connection.close()
    }

    fun connect() = Database.connect(dataSource).let {
        connection = dataSource.connection
        DatabaseSchema.writeSchema()
    }

}
