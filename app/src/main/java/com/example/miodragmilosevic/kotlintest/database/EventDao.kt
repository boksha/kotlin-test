package com.example.miodragmilosevic.kotlintest.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface EventDao {

    @Query("SELECT * from connectivityEvents")
    fun getAllConnectivityEvents(): List<ConnectivityEvent>

    @Insert(onConflict = REPLACE)
    fun insert(event: ConnectivityEvent)

    @Query("DELETE from connectivityEvents")
    fun deleteAllConnectivityEvents()

    @Query("SELECT * from activityEvents")
    fun getAllActivityEvents(): List<ActivityEvent>

    @Insert(onConflict = REPLACE)
    fun insert(event: ActivityEvent)

    @Query("DELETE from activityEvents")
    fun deleteAllActivityEvents()
}