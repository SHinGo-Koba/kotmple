package com.example.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.*

@Serializable
data class Customer(val id: Int, val firstName: String, val lastName: String, val email: String)

object Customers : Table() {
    val id = integer("id").autoIncrement()
    val firstName = varchar("first_name", length = 50)
    val lastName = varchar("last_name", length = 50)
    val email = varchar("email", length = 50)

    override val primaryKey = PrimaryKey(id)
}
