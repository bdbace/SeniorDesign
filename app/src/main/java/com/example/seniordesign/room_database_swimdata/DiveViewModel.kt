package com.example.seniordesign.room_database_swimdata

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.seniordesign.room_database.DiveRepository
import kotlinx.coroutines.launch

// Class extends AndroidViewModel and requires application as a parameter.
class DiveViewModel(application: Application) : AndroidViewModel(application) {

    // The ViewModel maintains a reference to the repository to get data.
    private val repository: DiveRepository
    // LiveData gives us updated words when they change.
    val allDives: LiveData<List<Dive>>

    init {
        // Gets reference to WordDao from WordRoomDatabase to construct
        // the correct WordRepository.
        val divesDao = DiveRoomDatabase.getDatabase(application, viewModelScope).diveDao()
        repository = DiveRepository(divesDao)
        allDives = repository.allDives
    }

    /**
     * The implementation of insert() in the database is completely hidden from the UI.
     * Room ensures that you're not doing any long running operations on
     * the main thread, blocking the UI, so we don't need to handle changing Dispatchers.
     * ViewModels have a coroutine scope based on their lifecycle called
     * viewModelScope which we can use here.
     */
    fun insert(dive: Dive) = viewModelScope.launch {
        repository.insert(dive)
    }
}