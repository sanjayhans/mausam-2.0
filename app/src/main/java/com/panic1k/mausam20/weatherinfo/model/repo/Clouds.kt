package com.panic1k.mausam20.weatherinfo.model.repo

import com.google.gson.annotations.SerializedName

data class Clouds(
    @SerializedName("all")
    val all: Int = 0
)
