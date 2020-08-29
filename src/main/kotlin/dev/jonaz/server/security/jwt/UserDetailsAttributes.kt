package dev.jonaz.server.security.jwt

data class UserDetailsAttributes(
        val user: Int
) {
    fun map() = mapOf(
            "user" to user
    )
}
