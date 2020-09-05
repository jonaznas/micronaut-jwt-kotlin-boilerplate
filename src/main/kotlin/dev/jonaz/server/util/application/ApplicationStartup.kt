package dev.jonaz.server.util.application

import dev.jonaz.server.config.HikariSourceConfig
import dev.jonaz.server.util.exposed.ExposedClient
import io.micronaut.context.event.ApplicationEventListener
import io.micronaut.runtime.server.event.ServerStartupEvent
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApplicationStartup @Inject constructor(
        private val exposedClient: ExposedClient
) : ApplicationEventListener<ServerStartupEvent> {

    override fun onApplicationEvent(event: ServerStartupEvent) {
        ApplicationServer(event.source.environment).setup()

        HikariSourceConfig.createDataSource().let {
            exposedClient.connect(it)
        }
    }
}
