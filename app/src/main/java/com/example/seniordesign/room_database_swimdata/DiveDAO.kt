package com.example.seniordesign.room_database_swimdata

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DiveDao {

    @Query("SELECT * from dive_table ORDER BY dive_num ASC")
    fun getAscendingDives(): LiveData<List<Dive>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(dive: Dive)

    @Query("DELETE FROM dive_table")
    suspend fun deleteAll()
}