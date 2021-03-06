package com.panic1k.mausam20.weatherinfo.model

import com.panic1k.mausam20.common.RequestCompleteListener
import com.panic1k.mausam20.weatherinfo.model.repo.City
import com.panic1k.mausam20.weatherinfo.model.repo.WeatherInfoResponse

interface WeatherInfoModel {
    fun getCityList(callback: RequestCompleteListener<MutableList<City>>)
    fun getWeatherInfo(cityId: Int, callback: RequestCompleteListener<WeatherInfoResponse>)
}
