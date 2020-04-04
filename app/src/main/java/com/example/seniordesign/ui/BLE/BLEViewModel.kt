package com.example.seniordesign.ui.BLE

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BLEViewModel : ViewModel()    {
    private val _text = MutableLiveData<String>().apply {
        value = "BLE Connection Here"
    }
val text: LiveData<String> = _text
}