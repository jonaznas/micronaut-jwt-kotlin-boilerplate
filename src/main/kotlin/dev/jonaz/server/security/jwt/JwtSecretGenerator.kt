package dev.jonaz.server.security.jwt

import dev.jonaz.server.util.tools.GenerateString

object JwtSecretGenerator {
    private lateinit var secret: String

    fun get(): String {
        return if (this::secret.isInitialized) secret
        else GenerateString.random(
                isWithLetters = true,
                isWithUppercase = true,
                isWithNumbers = true,
                isWithSpecial = true,
                length = 256
        )
    }
}
