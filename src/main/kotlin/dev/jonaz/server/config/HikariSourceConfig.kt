package dev.jonaz.server.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource

class HikariSourceConfig {

    companion object {
        private val dbHost: String? = System.getenv("database_host")
        private val dbPort: String? = System.getenv("database_port")
        private val dbName: String? = System.getenv("database_name")
        private val dbUser: String? = System.getenv("database_user")
        private val dbPass: String? = System.getenv("database_pass")
        private val url = "jdbc:postgresql://$dbHost:$dbPort/$dbName"

        private lateinit var config: HikariConfig

        fun createDataSource(): HikariDataSource {
            Class.forName("org.postgresql.Driver")

            config = HikariConfig()
            config.jdbcUrl = url
            config.username = dbUser
            config.password = dbPass

            return HikariDataSource(config)
        }
    }
}
