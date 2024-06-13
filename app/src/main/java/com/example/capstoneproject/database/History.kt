package com.example.capstoneproject.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class History(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo("id")
    var id: Int = 0,

    @ColumnInfo("workshopName")
    var workshopName: String = "",

    @ColumnInfo("Date")
    var date: String = "",
)
