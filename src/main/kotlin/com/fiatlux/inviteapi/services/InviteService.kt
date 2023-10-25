package com.fiatlux.inviteapi.services

import com.fiatlux.inviteapi.db.InviteLinkEntity
import com.fiatlux.inviteapi.db.TemporaryUserEntity
import com.fiatlux.inviteapi.models.InviteLinkDTO
import com.fiatlux.inviteapi.models.TemporaryUserDTO
import java.util.*
import org.jetbrains.exposed.sql.transactions.transaction

object InviteService {
    private val users = mutableListOf<TemporaryUserEntity>()
    private val inviteLinks = mutableListOf<InviteLinkEntity>()

    fun createInvite(userDTO: TemporaryUserDTO): InviteLinkEntity {
        lateinit var inviteLinkEntity: InviteLinkEntity
        transaction {
            val newUserEntity = TemporaryUserEntity.new {
                name = userDTO.name
                phoneNumber = userDTO.phoneNumber
                email = userDTO.email
            }

            inviteLinkEntity = InviteLinkEntity.new {
                user = newUserEntity
                isActive = true
            }
        }
        return inviteLinkEntity
    }

    fun acceptInvite(id: String): String {
        transaction {
            val inviteLinkEntity = InviteLinkEntity.findById(id.toInt()) ?: throw IllegalArgumentException("Invalid or expired link")
            val userEntity = TemporaryUserEntity.findById(inviteLinkEntity.user.id.value) ?: throw IllegalArgumentException("User not found")

            userEntity.isActive = true
            inviteLinkEntity.isActive = false
        }
        return "User activated successfully"
    }

}
