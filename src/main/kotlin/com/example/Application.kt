package com.example

import com.example.dao.DatabaseSingleton
import com.example.plugins.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main(args: Array<String>) {
    DatabaseSingleton.init()
    embeddedServer(Netty, commandLineEnvironment(args))
        .start(wait = true)
}

fun Application.module() {
    configureAuthentication(environment)
    configureSessions(environment)
    // Be sure to call configureSessions before configureRouting
    configureRouting(environment)
}
