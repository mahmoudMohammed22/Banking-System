package com.mahmoudtaskapp.bankingsystem.module

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "transform_table")
data class Transform(
    @PrimaryKey(autoGenerate = true)
    val id :Int = 0,
    @ColumnInfo(name = "sender")
    val senderName: String,
    @ColumnInfo(name = "recipient")
    val recipientName: String,
    @ColumnInfo(name = "amount")
    val amount: Int
)
