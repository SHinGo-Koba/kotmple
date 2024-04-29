package com.example.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Data class for the user session
 * This class is used to store the user session to interact with Google OAuth
 * @property state the state of the user session
 * @property token the token of the user session
 * @constructor Creates a user session
 */
data class UserSession(val state: String, val token: String)

/**
 * Data class for the user info
 * This class is used to store the user info to interact with Google OAuth
 * @property id the ID of the user
 * @property name the name of the user
 * @property givenName the given name of the user
 * @property familyName the family name of the user
 * @property picture the picture of the user
 * @property locale the locale of the user
 * @constructor Creates a user info
 */
@Serializable
data class UserInfo(
    val id: String,
    val name: String,
    @SerialName("given_name") val givenName: String,
    @SerialName("family_name") val familyName: String,
    val picture: String,
    val locale: String
)
