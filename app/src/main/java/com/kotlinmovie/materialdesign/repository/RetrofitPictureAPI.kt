package com.kotlinmovie.materialdesign.repository

import com.kotlinmovie.materialdesign.repository.data.EarthEpicServerResponseData
import com.kotlinmovie.materialdesign.repository.data.PictureResponseData
import com.kotlinmovie.materialdesign.repository.data.SputnikServerResponseData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitPictureAPI {

    @GET("planetary/apod")
    fun getPictureOfTheDay(
        @Query("date") date: String,
        @Query("api_key") apiKey: String)
    : Call<PictureResponseData>

    @GET("/mars-photos/api/v1/rovers/curiosity/photos")
    fun getMarsImageByDate(
        @Query("earth_date") earthDate: String,
        @Query("api_key") apiKey: String,
    ): Call<MarsPhotosResponseData>

    @GET("EPIC/api/natural")
    fun getEPIC(
        @Query("api_key") apiKey: String,
    ): Call<List<EarthEpicServerResponseData>>

    @GET("/planetary/earth/assets")
    fun getLandscapeImageFromSputnik(
        @Query("lon") lon: Float,
        @Query("lat") lat: Float,
        @Query("date") dateString: String,
        @Query("dim") dim: Float,
        @Query("api_key") apiKey: String
    ): Call<SputnikServerResponseData>
}

