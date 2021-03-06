package com.kotlinmovie.materialdesign.repository

import com.google.gson.annotations.SerializedName

data class MarsPhotosResponseData(
    @field:SerializedName("photos") val photos: ArrayList<MarsServerResponseData>,
)

data class MarsServerResponseData(
    @field:SerializedName("img_src") val imgSrc: String?,
    @field:SerializedName("earth_date") val earth_date: String?,
)
