package com.kotlinmovie.materialdesign.repository

import com.google.gson.GsonBuilder
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoRepositoryImpl {

    private val baseUrl = "https://api.nasa.gov/"
    fun getRetrofitImpl():RetrofitPictureAPI{
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()
        return retrofit.create(RetrofitPictureAPI::class.java)
    }


}