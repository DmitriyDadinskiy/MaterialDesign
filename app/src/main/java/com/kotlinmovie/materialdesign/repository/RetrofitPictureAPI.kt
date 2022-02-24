package com.kotlinmovie.materialdesign.repository

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitPictureAPI {

    @GET("planetary/apod")
    fun getPictureOfTheDay(
        @Query("date") date: String,
        @Query("api_key") apiKey: String)
    : Call<PictureResponseData>
}
//https://api.nasa.gov/planetary/apod
// ?date=2020-02-01
// &api_key=ZN4hEfubb9aL0eROMvhhEeKhcgPa0e8myfoIZ19P