package com.example.plugins

import com.example.routes.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*

fun Application.configureRouting(env: ApplicationEnvironment) {
    install(ContentNegotiation) {
        json()
    }

    routing {
        // Endpoints for the base routes
        baseRoutes(env)
        // Endpoints for the OAuth
        oauthRoutes()
        // Endpoints for the customer
        customerRouting()
    }
}
