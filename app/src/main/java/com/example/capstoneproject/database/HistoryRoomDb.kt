package com.example.capstoneproject.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [History::class], version = 1)
abstract class HistoryRoomDb: RoomDatabase() {
    abstract fun historyDao(): HistoryDao

    companion object {
        @Volatile
        private var INSTANCE: HistoryRoomDb? = null

        @JvmStatic
        fun getDatabase(context: Context): HistoryRoomDb {
            if (INSTANCE == null) {
                synchronized(HistoryRoomDb::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        HistoryRoomDb::class.java,
                        "history_database"
                    ).build()
                }
            }
            return INSTANCE as HistoryRoomDb
        }
    }
}