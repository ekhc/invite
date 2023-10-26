package com.fiatlux.inviteapi

import com.fiatlux.inviteapi.db.InviteLinkTable
import com.fiatlux.inviteapi.db.TemporaryUserTable
import com.fiatlux.inviteapi.routes.registerInviteRoutes
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import kotlinx.serialization.json.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun main() {
    embeddedServer(Netty, port = 8080, module = Application::module).start(wait = true)
}

fun Application.module() {
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        })
    }

    // Connect to H2 database
    Database.connect("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;", driver = "org.h2.Driver")

    // Create tables if they don't exist
    transaction {
        SchemaUtils.create(TemporaryUserTable, InviteLinkTable) // Replace with your table names
    }

    registerInviteRoutes()
}
