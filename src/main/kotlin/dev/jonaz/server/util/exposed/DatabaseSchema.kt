package dev.jonaz.server.util.exposed

import org.atteo.classindex.ClassIndex
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.transactions.transaction
import javax.inject.Singleton

@Singleton
class DatabaseSchema {
    private val domains = ClassIndex.getAnnotated(CreateSchema::class.java)
    private val tables = mutableListOf<Table>()

    init {
        setTables()
    }

    fun writeSchema() = transaction {
        SchemaUtils.create(*tables.toTypedArray())
    }

    private fun setTables() = domains.forEach {
        val table = castDomain(it)
                    ?: return@forEach

        tables.add(table)
    }

    private fun castDomain(clazz: Class<*>) = try {
        clazz.kotlin.objectInstance as Table
    } catch (_: Exception) {
        null
    }
}
