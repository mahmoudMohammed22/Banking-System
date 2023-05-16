package com.mahmoudtaskapp.bankingsystem.roomdatabase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.mahmoudtaskapp.bankingsystem.module.Transform
import kotlinx.coroutines.flow.Flow


@Dao
interface TransformDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransform(transform: Transform)

    @Query("SELECT * from transform_table ORDER BY sender ASC")
    fun getTransform(): Flow<List<Transform>>
}