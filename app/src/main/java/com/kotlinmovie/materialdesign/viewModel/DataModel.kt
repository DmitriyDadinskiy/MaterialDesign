package com.kotlinmovie.materialdesign.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class DataModel:ViewModel() {

    val positionChips: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

}