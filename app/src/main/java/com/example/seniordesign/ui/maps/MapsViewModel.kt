package com.example.seniordesign.ui.maps

import android.app.Application
import androidx.lifecycle.*
import com.example.seniordesign.room_database.DiveRepository
import com.example.seniordesign.room_database_swimdata.Dive
import com.example.seniordesign.room_database_swimdata.DiveRoomDatabase
import kotlinx.coroutines.launch

class MapsViewModel(application: Application) : AndroidViewModel(application) {
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

    fun insert(dive: Dive) = viewModelScope.launch {
        repository.insert(dive)
    }

     
    private val _text = MutableLiveData<String>().apply {
        value = "Dive Maps Go Here"
    }
    val text: LiveData<String> = _text



}