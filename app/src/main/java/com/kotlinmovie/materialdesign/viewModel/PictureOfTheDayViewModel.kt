package com.kotlinmovie.materialdesign.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kotlinmovie.materialdesign.BuildConfig
import com.kotlinmovie.materialdesign.repository.data.EarthEpicServerResponseData
import com.kotlinmovie.materialdesign.repository.MarsPhotosResponseData
import com.kotlinmovie.materialdesign.repository.data.PictureResponseData
import com.kotlinmovie.materialdesign.repository.RemoRepositoryImpl
import com.kotlinmovie.materialdesign.repository.data.SputnikServerResponseData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PictureOfTheDayViewModel(
    private val liveData: MutableLiveData<PictureOfTheDayState> = MutableLiveData(),
    private val remoRepositoryImpl: RemoRepositoryImpl = RemoRepositoryImpl(),

    private val liveDataState: MutableLiveData<PictureState> = MutableLiveData(),

    ) : ViewModel() {


    fun getLiveData(): LiveData<PictureOfTheDayState> {
        return liveData
    }

    fun getLiveDataState(): LiveData<PictureState> {
        return liveDataState
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
                            onError.invoke(Throwable(response.message()))
                        }
                    }

                    override fun onFailure(call: Call<PictureResponseData>, t: Throwable) {
                        onError.invoke(Throwable())

                    }
                }
            )

    }

    fun getMarsPicture(
        earthDate: String,
        onError: (Throwable) -> Unit
    ) {
        liveDataState.postValue(PictureState.Loading(null))

        remoRepositoryImpl.getRetrofitImpl().getMarsImageByDate(earthDate, BuildConfig.NASA_API_KEY)
            .enqueue(
                object : Callback<MarsPhotosResponseData> {
                    override fun onResponse(
                        call: Call<MarsPhotosResponseData>,
                        response: Response<MarsPhotosResponseData>,
                    ) {
                        if (response.isSuccessful && response.body() != null) {
                            response.body()?.let {
                                liveDataState.postValue(PictureState.SuccessMars(response.body()!!))
                            }
                        } else {
                            onError.invoke(Throwable(response.message()))
                        }
                    }

                    override fun onFailure(call: Call<MarsPhotosResponseData>, t: Throwable) {
                        onError.invoke(Throwable())
                    }

                },
            )
    }

    fun getEpicPicture(
        onError: (Throwable) -> Unit
    ) {
        liveDataState.postValue(PictureState.Loading(null))
        remoRepositoryImpl.getRetrofitImpl().getEPIC(BuildConfig.NASA_API_KEY).enqueue(
            object : Callback<List<EarthEpicServerResponseData>> {
                override fun onResponse(
                    call: Call<List<EarthEpicServerResponseData>>,
                    response: Response<List<EarthEpicServerResponseData>>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        response.body()?.let {
                            liveDataState.postValue(PictureState.SuccessEarthEpic(response.body()!!))
                        }
                    } else {
                        onError.invoke(Throwable(response.message()))
                    }
                }

                override fun onFailure(
                    call: Call<List<EarthEpicServerResponseData>>,
                    t: Throwable
                ) {
                    onError.invoke(Throwable())
                }

            }
        )
    }

    fun getSputnikPicture(
        lon: Float,
        lat: Float,
        dateString: String,
        dim: Float,
        onError: (Throwable) -> Unit
    ){
        liveDataState.postValue(PictureState.Loading(null))
        remoRepositoryImpl.getRetrofitImpl()
            .getLandscapeImageFromSputnik(lon, lat, dateString, dim, BuildConfig.NASA_API_KEY)
            .enqueue(object : Callback<SputnikServerResponseData>{
                override fun onResponse(
                    call: Call<SputnikServerResponseData>,
                    response: Response<SputnikServerResponseData>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        response.body()?.let {
                            liveDataState.postValue(PictureState.SuccessSputnik(response.body()!!))
                        }
                    } else {
                        onError.invoke(Throwable(response.message()))
                    }
                }

                override fun onFailure(call: Call<SputnikServerResponseData>, t: Throwable) {
                    onError.invoke(Throwable())
                }

            }
            )
    }
}
