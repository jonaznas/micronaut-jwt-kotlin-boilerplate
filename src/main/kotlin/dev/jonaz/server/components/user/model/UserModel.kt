package dev.jonaz.server.components.user.model

data class UserModel(
        val id: Int,
        val name: String,
        val password: String,
        val roles: List<String>
)
