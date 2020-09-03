package dev.jonaz.server.controller.stream

import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.sse.Event
import io.micronaut.scheduling.TaskExecutors
import io.micronaut.scheduling.annotation.ExecuteOn
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import io.reactivex.Emitter
import io.reactivex.Flowable
import io.reactivex.functions.BiFunction
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.time.LocalDateTime
import java.util.concurrent.Callable

@Controller("/stream/test")
@Secured(SecurityRule.IS_ANONYMOUS)
class TestStream {

    @ExecuteOn(TaskExecutors.IO)
    @Get(produces = [MediaType.TEXT_EVENT_STREAM])
    fun index(): Flowable<Event<EventData>> {
        val cancelTime = LocalDateTime.now().plusSeconds(10)

        return Flowable.generate(
                Callable { 0 }, BiFunction { i: Int, emitter: Emitter<Event<EventData>> ->

            runBlocking {
                val dateTime = LocalDateTime.now()
                val eventData = EventData(dateTime)

                emitter.onNext(Event.of(eventData))

                delay(1000L)

                if (dateTime.isAfter(cancelTime)) emitter.onComplete()
            }

            i
        })
    }

    data class EventData(
            val time: LocalDateTime
    )
}
