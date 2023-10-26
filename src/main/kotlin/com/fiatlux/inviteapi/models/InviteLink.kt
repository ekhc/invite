package com.fiatlux.inviteapi.models

import com.fiatlux.inviteapi.db.InviteLinkEntity
import com.fiatlux.inviteapi.db.TemporaryUserEntity
import kotlinx.serialization.Serializable

@Serializable
data class InviteLinkDTO(
    val id: Int,
    val userId: String,
    val isActive: Boolean = true
)

fun InviteLinkDTO.toEntity(): InviteLinkEntity {
    val userEntity = TemporaryUserEntity.findById(this.id)
        ?: throw IllegalArgumentException("User not found for id: ${this.id}")

    return InviteLinkEntity.new(this.id) {
        user = userEntity
        isActive = this@toEntity.isActive
    }
}