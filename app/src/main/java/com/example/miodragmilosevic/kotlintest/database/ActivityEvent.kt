package com.example.miodragmilosevic.kotlintest.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "activityEvents")
data class ActivityEvent(@PrimaryKey(autoGenerate = true) var id: Long?,
                             @ColumnInfo(name = "type") var type: String,
                             @ColumnInfo(name = "timestamp") var timestamp: Long
)