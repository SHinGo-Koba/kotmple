package com.example.models

import io.ktor.server.auth.*

data class LoginSession(val userID: String, val userName: String) : Principal
