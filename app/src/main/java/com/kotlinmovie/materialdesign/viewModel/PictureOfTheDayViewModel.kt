package com.kotlinmovie.materialdesign.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kotlinmovie.materialdesign.BuildConfig
import com.kotlinmovie.materialdesign.repository.PictureResponseData
import com.kotlinmovie.materialdesign.repository.RemoRepositoryImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PictureOfTheDayViewModel(
    private val liveData: MutableLiveData<PictureOfTheDayState> = MutableLiveData(),
    private val remoRepositoryImpl: RemoRepositoryImpl = RemoRepositoryImpl()

) : ViewModel() {


    fun getLiveData(): LiveData<PictureOfTheDayState> {
        return liveData
    }

    fun sendServerRequest(
        date: String,
        onError: (Throwable) -> Unit
    ) {
        liveData.postValue(PictureOfTheDayState.Loading(null))
        remoRepositoryImpl.getRetrofitImpl().getPictureOfTheDay(date, BuildConfig.NASA_API_KEY)
            .enqueue(
                object : Callback<PictureResponseData> {
                    override fun onResponse(
                        call: Call<PictureResponseData>,
                        response: Response<PictureResponseData>
                    ) {
                        if (response.isSuccessful && response.body() != null) {
                            response.body()?.let {
                                liveData.postValue(PictureOfTheDayState.Success(it))
                            }

                        } else {
                            onError.invoke(Throwable())
                        }
                    }

                    override fun onFailure(call: Call<PictureResponseData>, t: Throwable) {
                        onError.invoke(Throwable())

                    }
                }
            )

    }
}
