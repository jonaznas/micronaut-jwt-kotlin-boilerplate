package dev.jonaz.server.components.user.model

data class RefreshTokenModel(
        val token: String,
        val user: Int,
        val revoked: Boolean
)
