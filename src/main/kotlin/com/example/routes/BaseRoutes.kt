package com.example.routes

import com.example.models.UserInfo
import com.example.models.UserSession
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*

fun Route.baseRoutes() {
    val httpClient = HttpClient(CIO)

    get("/") {
        call.respondText("Login")
    }

    get("/home") {
        val userSession: UserSession? = getSession(call)
        if (userSession != null) {
            val userInfo: UserInfo = getPersonalGreeting(httpClient, userSession)
            call.respondText("Hello, ${userInfo.name}! Welcome home!")
        }
    }

    get("/{path}") {
        val userSession: UserSession? = getSession(call)
        if (userSession != null) {
            val userInfo: UserInfo = getPersonalGreeting(httpClient, userSession)
            call.respondText("Hello, ${userInfo.name}!")
        }
    }
}

private suspend fun getPersonalGreeting(
    httpClient: HttpClient,
    userSession: UserSession
): UserInfo = httpClient.get("https://www.googleapis.com/oauth2/v2/userinfo") {
    headers {
        append(HttpHeaders.Authorization, "Bearer ${userSession.token}")
    }
}.body()

private suspend fun getSession(
    call: ApplicationCall
): UserSession? {
    val userSession: UserSession? = call.sessions.get()
    //if there is no session, redirect to login
    if (userSession == null) {
        val redirectUrl = URLBuilder("http://0.0.0.0:8080/login").run {
            parameters.append("redirectUrl", call.request.uri)
            build()
        }
        call.respondRedirect(redirectUrl)
        return null
    }
    return userSession
}
