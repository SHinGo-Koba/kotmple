package com.example.routes

import com.example.dao.CustomersFacadeImpl
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.customerRouting() {
    route("/customers") {
        get {
            call.respond(mapOf("Customers" to CustomersFacadeImpl().allCustomers()))
        }

        get("{id?}") {
            // TODO
        }

        post {
            // TODO
        }

        put("{id?}") {
            // TODO
        }

        delete("{id?}") {
            // TODO
        }
    }
}
