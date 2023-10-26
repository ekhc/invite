package com.fiatlux.inviteapi.db

import com.fiatlux.inviteapi.models.InviteLinkDTO
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.*
import org.jetbrains.exposed.sql.SizedIterable


object TemporaryUserTable : IntIdTable() {
    val name = varchar("name", 255)
    val phoneNumber = varchar("phoneNumber", 20)
    val email = varchar("email", 255)
    val isActive = bool("isActive").default(false)
}

class TemporaryUserEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<TemporaryUserEntity>(TemporaryUserTable)

    var name by TemporaryUserTable.name
    var phoneNumber by TemporaryUserTable.phoneNumber
    var email by TemporaryUserTable.email
    var isActive by TemporaryUserTable.isActive

    // Custom getter for inviteLinks
    val inviteLinks: SizedIterable<InviteLinkEntity> get() = InviteLinkEntity.find { InviteLinkTable.userId eq id }
}

object InviteLinkTable : IntIdTable() {
    val userId = reference("user", TemporaryUserTable)
    val isActive = bool("isActive").default(true)
}

class InviteLinkEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<InviteLinkEntity>(InviteLinkTable)

    var user by TemporaryUserEntity referencedOn InviteLinkTable.userId
    var isActive by InviteLinkTable.isActive

    fun toDTO(): InviteLinkDTO {
        return InviteLinkDTO(
            id = this.id.value,  // Assuming `id` is of type `EntityID<Int>`
            userId = this.user.toString(),
            isActive = this.isActive
        )
    }
}
