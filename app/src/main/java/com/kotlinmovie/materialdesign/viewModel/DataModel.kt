package com.kotlinmovie.materialdesign.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class DataModel:ViewModel() {
    val filters = MutableLiveData<Set<String>>()

    private val _positionChips = MutableLiveData<String>()
    val positionChips: LiveData<String> get() = _positionChips

    fun positionChips(string: String){
        _positionChips.value = string
    }

}