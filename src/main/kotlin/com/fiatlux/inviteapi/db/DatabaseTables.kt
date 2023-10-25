package com.fiatlux.inviteapi.db

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.*


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
    var inviteLinks by InviteLinkEntity referencedOn InviteLinkTable.userId
}

object InviteLinkTable : IntIdTable() {
    val userId = reference("user", TemporaryUserTable)
    val isActive = bool("isActive").default(true)
}

class InviteLinkEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<InviteLinkEntity>(InviteLinkTable)

    var user by TemporaryUserEntity referencedOn InviteLinkTable.userId
    var isActive by InviteLinkTable.isActive
}
