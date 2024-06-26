package com.example.plugins

import com.example.models.LoginSession
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*

// See https://ktor.io/docs/oauth.html#oauth-configuration for more information
fun Application.configureAuthentication(env: ApplicationEnvironment) {
    install(Authentication) {
        val httpClient = HttpClient(CIO)
        val redirects = mutableMapOf<String, String>()

        oauth("auth-oauth-google") {
            // Configure oauth authentication
            urlProvider = {
                env.config.propertyOrNull("ktor.oauth2.google.urlProvider")?.getString()
                    ?: System.getenv("GOOGLE_URL_PROVIDER")
            }
            providerLookup = {
                OAuthServerSettings.OAuth2ServerSettings(
                    name = "google",
                    authorizeUrl = "https://accounts.google.com/o/oauth2/auth",
                    accessTokenUrl = "https://accounts.google.com/o/oauth2/token",
                    requestMethod = HttpMethod.Post,
                    clientId = env.config.propertyOrNull("ktor.oauth2.google.clientId")?.getString()
                        ?: System.getenv("GOOGLE_CLIENT_ID"),
                    clientSecret = env.config.propertyOrNull("ktor.oauth2.google.clientSecret")?.getString()
                        ?: System.getenv("GOOGLE_CLIENT_SECRET"),
                    defaultScopes = listOf("https://www.googleapis.com/auth/userinfo.profile"),
                    extraAuthParameters = listOf("access_type" to "offline"),
                    onStateCreated = { call, state ->
                        //saves new state with redirect url value
                        call.request.queryParameters["redirectUrl"]?.let {
                            redirects[state] = it
                        }
                    }
                )
            }
            client = httpClient
        }

        session<LoginSession>("auth-session") {
            validate { session ->
                if (session.userID.isNotEmpty() && session.userName.isNotEmpty()) {
                    session
                } else {
                    null
                }
            }
            challenge {
                call.respondRedirect("/login")
            }
        }
    }
}
