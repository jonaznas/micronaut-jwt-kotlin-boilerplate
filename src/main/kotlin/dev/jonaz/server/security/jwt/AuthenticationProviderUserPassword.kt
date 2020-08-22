package dev.jonaz.server.security.jwt

import io.micronaut.http.HttpRequest
import io.micronaut.security.authentication.*
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableEmitter
import org.reactivestreams.Publisher
import javax.inject.Singleton

@Singleton
class AuthenticationProviderUserPassword : AuthenticationProvider {

    override fun authenticate(httpRequest: HttpRequest<*>?, authenticationRequest: AuthenticationRequest<*, *>?): Publisher<AuthenticationResponse> {
        val identity = authenticationRequest?.identity ?: ""
        val secret = authenticationRequest?.secret ?: ""

        return Flowable.create({ emitter: FlowableEmitter<AuthenticationResponse> ->

            if (identity.equals("sherlock") && secret.equals("password")) {
                val userDetails = UserDetails(identity.toString(), ArrayList())
                emitter.onNext(userDetails)
                emitter.onComplete()
            } else {
                emitter.onError(AuthenticationException(AuthenticationFailed()))
            }

        }, BackpressureStrategy.ERROR)
    }
}
