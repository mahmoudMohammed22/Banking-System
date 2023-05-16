package com.mahmoudtaskapp.bankingsystem.repositrey

import com.mahmoudtaskapp.bankingsystem.module.Customer
import com.mahmoudtaskapp.bankingsystem.module.Transform
import com.mahmoudtaskapp.bankingsystem.roomdatabase.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext


class Respiratory(val database : AppDatabase) {

    val getCustomers = database.customerDao().getCustomers()

    val getTransform = database.transformDao().getTransform()

    fun getCustomer(id:Int):Flow<Customer>{
        return database.customerDao().getCustomer(id)
    }

    suspend fun updateCustomer(customer: Customer){
        database.customerDao().updateCustomer(customer)
    }

    suspend fun insertTransform(transform: Transform){
        database.transformDao().insertTransform(transform)
    }







}