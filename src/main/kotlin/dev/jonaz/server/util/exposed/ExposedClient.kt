package dev.jonaz.server.util.exposed

import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import java.sql.Connection
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExposedClient @Inject constructor(
        private val databaseSchema: DatabaseSchema
) {
    companion object {
        private lateinit var connection: Connection

        fun closeConnection() = connection.close()
    }

    fun connect(dataSource: HikariDataSource) = Database.connect(dataSource).let {
        connection = dataSource.connection
        databaseSchema.writeSchema()
    }
}
