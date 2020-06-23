package com.panic1k.mausam20.weatherinfo.model

import com.panic1k.mausam20.weatherinfo.model.repo.City
import com.panic1k.mausam20.weatherinfo.model.repo.WeatherInfoResponse

interface WeatherInfoModel {
    fun getCityList(modelCallback: ModelCallback<List<City>>)
    fun getWeatherInfo(cityId: Int, modelCallback: ModelCallback<WeatherInfoResponse>)
}
