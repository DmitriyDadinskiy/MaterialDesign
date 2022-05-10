package com.kotlinmovie.materialdesign.ui.recyclerView

const val TYPE_EARTH = 1
const val TYPE_MARS = 2
const val TYPE_HEADER = 3

data class RecyclerData(
    val name: String = "Text",
    val description: String? = null,
    val type: Int = TYPE_EARTH
)
