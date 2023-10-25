package com.fiatlux.inviteapi.routes

import com.fiatlux.inviteapi.models.TemporaryUserDTO
import com.fiatlux.inviteapi.services.InviteService
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.inviteRoutes() {
    route("/invites") {
        post("/create") {
            val user = call.receive<TemporaryUserDTO>()
            val inviteLink = InviteService.createInvite(user)
            call.respond(inviteLink)
        }

        post("/accept/{id}") {
            val id = call.parameters["id"] ?: return@post call.respondText("Invalid invite link")
            val result = InviteService.acceptInvite(id)
            call.respond(result)
        }
    }
}

fun Application.registerInviteRoutes() {
    routing {
        inviteRoutes()
    }
}
