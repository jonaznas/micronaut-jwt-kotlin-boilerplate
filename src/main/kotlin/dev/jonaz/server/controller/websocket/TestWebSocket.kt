package dev.jonaz.server.controller.websocket

import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import io.micronaut.websocket.WebSocketBroadcaster
import io.micronaut.websocket.WebSocketSession
import io.micronaut.websocket.annotation.OnClose
import io.micronaut.websocket.annotation.OnMessage
import io.micronaut.websocket.annotation.OnOpen
import io.micronaut.websocket.annotation.ServerWebSocket

@ServerWebSocket("/test2/{parameter}")
@Secured(SecurityRule.IS_AUTHENTICATED)
class TestWebSocket(private val broadcaster: WebSocketBroadcaster) {

    @OnOpen
    fun onOpen(parameter: String, session: WebSocketSession) {
        println("websocket onOpen")
    }

    @OnMessage
    fun onMessage(parameter: String, message: String, session: WebSocketSession) {
        println("websocket onMessage")
    }

    @OnClose
    fun onClose(parameter: String, session: WebSocketSession) {
        println("websocket onClose")
    }
}
