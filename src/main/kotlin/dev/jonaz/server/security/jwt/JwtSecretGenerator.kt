package dev.jonaz.server.security.jwt

import dev.jonaz.server.util.tools.GenerateString
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class JwtSecretGenerator @Inject constructor(
        private val generateString: GenerateString
) {
    private lateinit var secret: String

    fun get(): String {
        return if (this::secret.isInitialized) secret
        else generateString.random(
                isWithLetters = true,
                isWithUppercase = true,
                isWithNumbers = true,
                isWithSpecial = true,
                length = 256
        )
    }
}
