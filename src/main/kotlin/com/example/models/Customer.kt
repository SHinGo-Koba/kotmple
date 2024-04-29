package com.example.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.*

/**
 * Data class for the customer
 * This class is used to store the customer information
 * @property id the ID of the customer
 * @property firstName the first name of the customer
 * @property lastName the last name of the customer
 * @property email the email of the customer
 * @constructor Creates a customer
 */
@Serializable
data class Customer(val id: Int, val firstName: String, val lastName: String, val email: String)

object Customers : Table() {
    val id = integer("id").autoIncrement()
    val firstName = varchar("first_name", length = 50)
    val lastName = varchar("last_name", length = 50)
    val email = varchar("email", length = 50)

    override val primaryKey = PrimaryKey(id)
}
