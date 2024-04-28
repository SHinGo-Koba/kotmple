package com.example.dao

import com.example.dao.DatabaseSingleton.dbQuery
import com.example.models.Customer
import com.example.models.Customers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class CustomersFacadeImpl : CustomersFacade {
    private fun resultRowToCustomer(row: ResultRow) = Customer(
        id = row[Customers.id],
        firstName = row[Customers.firstName],
        lastName = row[Customers.lastName],
        email = row[Customers.email]
    )

    override suspend fun allCustomers(): List<Customer> = dbQuery {
        Customers.selectAll().map(::resultRowToCustomer)
    }

    override suspend fun customerById(id: Int): Customer? = dbQuery {
        Customers.select { Customers.id eq id }
            .map(::resultRowToCustomer)
            .singleOrNull()
    }

    override suspend fun addCustomer(firstName: String, lastName: String, email: String): Customer = dbQuery {
        val insertStatement = Customers.insert {
            it[Customers.firstName] = firstName
            it[Customers.lastName] = lastName
            it[Customers.email] = email
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToCustomer)
            ?: throw IllegalStateException("Something went wrong when adding a new Customer")
    }

    override suspend fun deleteCustomer(id: Int): Boolean = dbQuery {
        Customers.deleteWhere { Customers.id eq id } > 0
    }

    override suspend fun updateCustomer(id: Int, firstName: String, lastName: String, email: String): Boolean =
        dbQuery {
            Customers.update({ Customers.id eq id }) {
                it[Customers.firstName] = firstName
                it[Customers.lastName] = lastName
                it[Customers.email] = email
            } > 0
        }
}