package com.panic1k.mausam20.weatherinfo.model.repo

import com.google.gson.annotations.SerializedName

data class Coordinates(
    @SerializedName("long")
    val lon: Double = 0.0,
    @SerializedName("lat")
    val lat: Double = 0.0
)
