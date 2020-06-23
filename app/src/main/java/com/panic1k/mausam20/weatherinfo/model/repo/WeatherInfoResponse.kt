package com.panic1k.mausam20.weatherinfo.model.repo

import com.google.gson.annotations.SerializedName

data class WeatherInfoResponse(
    @SerializedName("coord")
    val coord: Coordinates = Coordinates(),
    @SerializedName("weather")
    val weather: List<Weather> = listOf(),
    @SerializedName("base")
    val base: String = "",
    @SerializedName("main")
    val main: Main = Main(),
    @SerializedName("visibility")
    val visibility: Int = 0,
    @SerializedName("wind")
    val wind: Wind = Wind(),
    @SerializedName("clouds")
    val clouds: Clouds = Clouds(),
    @SerializedName("dt")
    val dt: Int = 0,
    @SerializedName("weatherSys")
    val sys: WeatherSys = WeatherSys(),
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("cod")
    val cod: Int = 0
)
