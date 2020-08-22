package dev.jonaz.server.util.application

import io.micronaut.context.env.Environment
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class ApplicationServer(
        private val env: Environment
) {

    companion object {
        lateinit var logger: Logger
        lateinit var environment: Environment
    }

    fun setup() {
        logger = LoggerFactory.getLogger(this::class.java)
        environment = env
    }
}
