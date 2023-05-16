package com.mahmoudtaskapp.bankingsystem.module

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "customers_table")
data class Customer(
    @PrimaryKey(autoGenerate = true)
    val id :Int = 0,
    @ColumnInfo(name = "name")
    val customerName: String,
    @ColumnInfo(name = "balance")
    val balance: Int,
    @ColumnInfo(name = "accountNumber")
    val accountNum: Long
)
