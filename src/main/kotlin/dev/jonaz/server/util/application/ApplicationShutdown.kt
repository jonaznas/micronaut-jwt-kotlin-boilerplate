package dev.jonaz.server.util.application

import dev.jonaz.server.util.exposed.ExposedClient
import io.micronaut.context.event.ApplicationEventListener
import io.micronaut.runtime.server.event.ServerShutdownEvent
import javax.inject.Singleton

@Singleton
class ApplicationShutdown : ApplicationEventListener<ServerShutdownEvent> {

    override fun onApplicationEvent(event: ServerShutdownEvent?) {
        ExposedClient.closeConnection()
    }
}
