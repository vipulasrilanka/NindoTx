package net.nonimi.nindotx.data

import kotlinx.serialization.Serializable

@Serializable
data class AuthRequest(
    val username: String,
    val password: String,
    val expiresInMins: Int = 30
)