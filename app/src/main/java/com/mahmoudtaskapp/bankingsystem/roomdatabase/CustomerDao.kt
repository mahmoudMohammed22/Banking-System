package com.mahmoudtaskapp.bankingsystem.roomdatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.mahmoudtaskapp.bankingsystem.module.Customer
import kotlinx.coroutines.flow.Flow

@Dao
interface CustomerDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCustomer(customer: List<Customer>)

    @Update
    suspend fun updateCustomer(customer: Customer)

    @Query("SELECT * from customers_table ORDER BY name ASC")
    fun getCustomers(): Flow<List<Customer>>

    @Query("SELECT * from customers_table WHERE id = :id")
    fun getCustomer(id: Int): Flow<Customer>


    @Query("SELECT * from customers_table WHERE name = :name")
    suspend fun getReceiverData(name:String): Customer

    @Query("SELECT * from customers_table WHERE id <> :customerId")
     suspend fun getReceiverWithoutThisId(customerId: Int): List<Customer>






}