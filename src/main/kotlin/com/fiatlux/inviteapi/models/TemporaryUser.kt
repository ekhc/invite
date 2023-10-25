package com.fiatlux.inviteapi.models

data class TemporaryUserDTO(val id: String, val name: String, val phoneNumber: String, val email: String, val isActive: Boolean = false)
