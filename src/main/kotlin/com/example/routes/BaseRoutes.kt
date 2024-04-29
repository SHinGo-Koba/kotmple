package com.example.routes

import com.example.models.LoginSession
import com.example.models.UserInfo
import com.example.models.UserSession
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import io.ktor.client.plugins.contentnegotiation.*

fun Route.baseRoutes(env: ApplicationEnvironment) {
    val httpClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
    }

    get("/") {
        call.respondText("Welcome to Kotmple!")
    }

    get("/home") {
        val userSession: UserSession? = getSession(call, env)

        userSession ?: return@get call.respondText { "Login First" }

        val userInfo: UserInfo = getPersonalGreeting(httpClient, userSession)
        call.sessions.set(LoginSession(userID = userInfo.id, userName = userInfo.name))
        call.respondText("Hello, ${userInfo.name}! Welcome home!")
    }

    get("/logout") {
        call.sessions.clear<LoginSession>()
        call.respondText("Logged out")
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
    call: ApplicationCall,
    env: ApplicationEnvironment
): UserSession? {
    val userSession: UserSession? = call.sessions.get()

    return userSession ?: run {
        val host = env.config.propertyOrNull("ktor.deployment.hostURL")?.getString() ?: "http://0.0.0.0"
        val redirectUrl = URLBuilder("$host:8080/login").run {
            parameters.append("redirectUrl", call.request.uri)
            build()
        }
        call.respondRedirect(redirectUrl)
        null
    }
}
