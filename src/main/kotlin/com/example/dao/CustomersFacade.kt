package com.example.dao

import com.example.models.Customer

interface CustomersFacade {
    suspend fun allCustomers(): List<Customer>
    suspend fun customerById(id: Int): Customer?
    suspend fun addCustomer(firstName: String, lastName: String, email: String): Customer
    suspend fun deleteCustomer(id: Int): Boolean
    suspend fun updateCustomer(id: Int, firstName: String, lastName: String, email: String): Boolean
}
