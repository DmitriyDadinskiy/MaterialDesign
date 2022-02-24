package com.kotlinmovie.materialdesign.viewModel

import com.kotlinmovie.materialdesign.repository.PictureResponseData

sealed class PictureOfTheDayState {

    data class Success(val serverResponseData: PictureResponseData) : PictureOfTheDayState()
    data class Error(val error: Throwable) : PictureOfTheDayState()
    data class Loading(val progress: Int?) : PictureOfTheDayState()

}