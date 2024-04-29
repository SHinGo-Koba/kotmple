package com.example.plugins

import com.example.models.LoginSession
import com.example.models.UserSession
import io.ktor.server.application.*
import io.ktor.server.sessions.*
import io.ktor.util.*

fun Application.configureSessions(env: ApplicationEnvironment) {
    install(Sessions) {
        val secretKey = env.config.property("ktor.application.secret_key").getString()
        val encryptKey = env.config.property("ktor.application.encrypt_key").getString()
        val secretSignKey = hex(secretKey)
        val secretEncryptKey = hex(encryptKey)

        cookie<LoginSession>("login_session", SessionStorageMemory()) {
            cookie.path = "/"
            cookie.maxAgeInSeconds = 60 * 60
            transform(SessionTransportTransformerEncrypt(secretEncryptKey, secretSignKey))
        }

        // For Google OAuth
        cookie<UserSession>("user_session")
    }
}
