package com.example.routes

import com.example.models.UserSession
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*

fun Route.oauthRoutes() {
    val redirects = mutableMapOf<String, String>() // Define redirects map

    authenticate("auth-oauth-google") {
        get("/login") {
            // Redirects to 'authorizeUrl' automatically
        }

        get("/callback") {
            val currentPrincipal: OAuthAccessTokenResponse.OAuth2? = call.principal()
            // redirects home if the url is not found before authorization
            currentPrincipal?.let { principal ->
                principal.state?.let { state ->
                    call.sessions.set(UserSession(state, principal.accessToken))
                    redirects[state]?.let { redirect: String -> // Specify type for redirect
                        call.respondRedirect(redirect, false) // Specify type for respondRedirect
                    }
                }
            } ?: call.respondRedirect("/home", false) // Specify type for respondRedirect
        }
    }
}
