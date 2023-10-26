package com.fiatlux.inviteapi.models

import kotlinx.serialization.Serializable

@Serializable
data class TemporaryUserDTO(
    val id: Int? = null,
    val name: String,
    val phoneNumber: String,
    val email: String,
    val isActive: Boolean = false
)
