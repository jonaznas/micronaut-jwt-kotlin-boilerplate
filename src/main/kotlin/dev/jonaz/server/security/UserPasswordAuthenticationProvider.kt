package dev.jonaz.server.security

import at.favre.lib.crypto.bcrypt.BCrypt
import dev.jonaz.server.components.user.UserAccount
import dev.jonaz.server.components.user.UserRegistration
import dev.jonaz.server.security.jwt.UserDetailsAttributes
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
            val identity = authenticationRequest.identity.toString()
            val secret = authenticationRequest.secret.toString()

            // Fail if username or password are not in a valid format
            UserRegistration.validate(identity, secret).let {
                if (it.first.not()) {
                    emitter.onError(AuthenticationException(AuthenticationFailed()))
                    return@create
                }
            }

            // Fail if user does not exist or return the user
            val user = UserAccount.get(identity).let {
                if (it.size != 1) {
                    emitter.onError(AuthenticationException(AuthenticationFailed()))
                    return@create
                }

                it.get(0)
            }

            // Fail if password cannot be verified
            BCrypt.verifyer().verify(secret.toCharArray(), user.password).let {
                if (it.verified.not()) {
                    emitter.onError(AuthenticationException(AuthenticationFailed()))
                    return@create
                }
            }

            // Permit user login
            val userDetails = UserDetails(
                    user.name,
                    user.roles,
                    UserDetailsAttributes(
                            userId = user.id
                    ).map()
            )

            emitter.onNext(userDetails)
            emitter.onComplete()

        }, BackpressureStrategy.ERROR)
    }
}
