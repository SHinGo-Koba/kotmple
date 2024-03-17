package com.example.plugins

import com.example.routes.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    install(ContentNegotiation) {
        json()
    }

    routing {
        customerRouting()
        orderRouting()
        oauthRoutes()
        baseRoutes()
    }
}
