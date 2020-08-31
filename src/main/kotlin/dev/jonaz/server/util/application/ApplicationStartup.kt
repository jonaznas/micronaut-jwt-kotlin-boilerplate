package dev.jonaz.server.util.application

import dev.jonaz.server.config.HikariSourceConfig
import dev.jonaz.server.util.exposed.ExposedClient
import io.micronaut.context.event.ApplicationEventListener
import io.micronaut.runtime.server.event.ServerStartupEvent
import javax.inject.Singleton

@Singleton
class ApplicationStartup : ApplicationEventListener<ServerStartupEvent> {

    override fun onApplicationEvent(event: ServerStartupEvent) {
        ApplicationServer(event.source.environment).setup()

        HikariSourceConfig.createDataSource().let {
            ExposedClient(it).connect()
        }
    }
}
