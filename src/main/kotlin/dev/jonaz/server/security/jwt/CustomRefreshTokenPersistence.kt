package dev.jonaz.server.security.jwt

import dev.jonaz.server.components.user.UserAccount
import dev.jonaz.server.components.user.UserRefreshToken
import dev.jonaz.server.domain.UserRefreshTokenDomain
import io.micronaut.runtime.event.annotation.EventListener
import io.micronaut.security.authentication.UserDetails
import io.micronaut.security.errors.IssuingAnAccessTokenErrorCode
import io.micronaut.security.errors.OauthErrorResponseException
import io.micronaut.security.token.event.RefreshTokenGeneratedEvent
import io.micronaut.security.token.refresh.RefreshTokenPersistence
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableEmitter
import org.reactivestreams.Publisher
import javax.inject.Singleton

@Singleton
class CustomRefreshTokenPersistence : RefreshTokenPersistence {
    private val table = UserRefreshTokenDomain

    @EventListener
    override fun persistToken(event: RefreshTokenGeneratedEvent?) {
        when (null) {
            event -> return
            event.refreshToken -> return
            event.userDetails -> return
            event.userDetails.username -> return
        }

        val payload = event?.refreshToken ?: return
        val user = UserAccount.get(event.userDetails.username).get(0).id

        UserRefreshToken.save(payload, user)
    }

    override fun getUserDetails(refreshToken: String): Publisher<UserDetails> {
        return Flowable.create({ emitter: FlowableEmitter<UserDetails> ->
            val token = UserRefreshToken.get(refreshToken)

            when {
                token.size.equals(0) -> emitter.onError(OauthErrorResponseException(IssuingAnAccessTokenErrorCode.INVALID_GRANT, "refresh token not found", null))

                token.get(0).revoked -> emitter.onError(OauthErrorResponseException(IssuingAnAccessTokenErrorCode.INVALID_GRANT, "refresh token revoked", null))

                else                 -> {
                    val userId = token.get(0).user.toString()
                    val userDetails = UserDetails(userId, listOf(), UserDetailsAttributes(5).map())

                    emitter.onNext(userDetails)
                    emitter.onComplete()
                }
            }

        }, BackpressureStrategy.ERROR)
    }
}
