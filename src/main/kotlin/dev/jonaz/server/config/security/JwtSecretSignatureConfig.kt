package dev.jonaz.server.config.security

import com.nimbusds.jose.JWSAlgorithm
import dev.jonaz.server.security.jwt.JwtSecretGenerator
import io.micronaut.security.token.jwt.signature.secret.SecretSignatureConfiguration
import javax.inject.Singleton

@Singleton
class JwtSecretSignatureConfig : SecretSignatureConfiguration("generator") {

    override fun getJwsAlgorithm(): JWSAlgorithm {
        return JWSAlgorithm.HS512
    }

    override fun getSecret(): String {
        return JwtSecretGenerator.get()
    }

    override fun isBase64(): Boolean {
        return false
    }
}
