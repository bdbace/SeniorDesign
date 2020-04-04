package com.example.seniordesign.room_database

import androidx.lifecycle.LiveData
import com.example.seniordesign.room_database_swimdata.Dive
import com.example.seniordesign.room_database_swimdata.DiveDao

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class DiveRepository(private val diveDao: DiveDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allDives: LiveData<List<Dive>> = diveDao.getAscendingDives()

    suspend fun insert(dive: Dive) {
        diveDao.insert(dive)
    }
}