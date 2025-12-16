package com.example.finalproject.data.snacks

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SnackDao {

    @Query("SELECT * FROM snacks")
    fun getAllSnacks(): Flow<List<SnackEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSnack(snack: SnackEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(snacks: List<SnackEntity>)

    @Update
    suspend fun updateSnack(snack: SnackEntity)

    @Query("SELECT COUNT(*) FROM snacks")
    suspend fun count(): Int
}
