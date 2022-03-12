package com.kotlinmovie.materialdesign.viewModel

import com.kotlinmovie.materialdesign.repository.data.EarthEpicServerResponseData
import com.kotlinmovie.materialdesign.repository.MarsPhotosResponseData
import com.kotlinmovie.materialdesign.repository.data.SputnikServerResponseData


sealed class PictureState {

    data class SuccessSputnik(val serverResponseDataSputnik: SputnikServerResponseData) : PictureState()
    data class SuccessEarthEpic (val serverResponseData: List<EarthEpicServerResponseData>) : PictureState()
    data class SuccessMars(val serverResponseDataMars: MarsPhotosResponseData) : PictureState()
    data class Error(val error: Throwable) : PictureState()
    data class Loading(val progress: Int?) : PictureState()

}