package dev.jonaz.server.security.jwt

data class UserDetailsAttributes(
        val userId: Int
) {
    fun map() = mapOf(
            "userId" to userId
    )
}
