package com.example.capstoneproject.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface HistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addHistory(history: History)

    @Query("SELECT * FROM history")
    fun getHistory(): LiveData<List<History>>
}