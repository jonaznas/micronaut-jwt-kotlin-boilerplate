package dev.jonaz.server.security.jwt

import io.micronaut.http.HttpRequest
import io.micronaut.security.authentication.*
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableEmitter
import org.reactivestreams.Publisher
import javax.inject.Singleton

@Singleton
class UserPasswordAuthenticationProvider : AuthenticationProvider {

    override fun authenticate(httpRequest: HttpRequest<*>?, authenticationRequest: AuthenticationRequest<*, *>): Publisher<AuthenticationResponse> {
        return Flowable.create({ emitter: FlowableEmitter<AuthenticationResponse> ->

            if (authenticationRequest.identity == "sherlock" && authenticationRequest.secret == "password") {
                val userDetails = UserDetails(
                        authenticationRequest.identity as String,
                        listOf("ROLE_USER"),
                        UserDetailsAttributes(5).map()
                )

                val attributes = mapOf("user" to 5)
                userDetails.setAttributes(attributes)

                emitter.onNext(userDetails)
                emitter.onComplete()
            } else {
                emitter.onError(AuthenticationException(AuthenticationFailed()))
            }

        }, BackpressureStrategy.ERROR)
    }
}
