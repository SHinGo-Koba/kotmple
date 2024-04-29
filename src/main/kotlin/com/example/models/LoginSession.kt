package com.example.models

import io.ktor.server.auth.*

/**
 * Data class for the login session
 * This class is used to store the login session to interact with the user
 * @property userID the user ID
 * @property userName the username
 * @constructor Creates a login session
 */
data class LoginSession(val userID: String, val userName: String) : Principal
