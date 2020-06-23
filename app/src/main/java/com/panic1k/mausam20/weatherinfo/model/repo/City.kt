package com.panic1k.mausam20.weatherinfo.model.repo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class City(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("country")
    val country: String = ""
) : Serializable

