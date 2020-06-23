package com.panic1k.mausam20.weatherinfo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.panic1k.mausam20.common.RequestCompleteListener
import com.panic1k.mausam20.utils.kelvinToCelsius
import com.panic1k.mausam20.utils.unixTimestampToDateTimeString
import com.panic1k.mausam20.utils.unixTimestampToTimeString
import com.panic1k.mausam20.weatherinfo.model.WeatherInfoModel
import com.panic1k.mausam20.weatherinfo.model.repo.City
import com.panic1k.mausam20.weatherinfo.model.repo.WeatherData
import com.panic1k.mausam20.weatherinfo.model.repo.WeatherInfoResponse


class WeatherInfoViewModel : ViewModel() {
    /**
     * In our project, we have used different LiveData for success and failure.
     * both using a single LiveData. Another good approach may be handle errors in BaseActivity.
     */
    val cityListLiveData = MutableLiveData<MutableList<City>>()
    val cityListFailureLiveData = MutableLiveData<String>()
    val weatherInfoLiveData = MutableLiveData<WeatherData>()
    val weatherInfoFailureLiveData = MutableLiveData<String>()
    val progressBarLiveData = MutableLiveData<Boolean>()

    /**we can inject the instance of Model in Constructor using dependency injection.
     */
    fun getCityList(model: WeatherInfoModel) {

        model.getCityList(object :
            RequestCompleteListener<MutableList<City>> {
            override fun onRequestSuccess(data: MutableList<City>) {
                cityListLiveData.postValue(data)
            }

            override fun onRequestFailed(errorMessage: String) {
                cityListFailureLiveData.postValue(errorMessage)
            }
        })
    }

    /**we can inject the instance of Model in Constructor using dependency injection here
     */
    fun getWeatherInfo(cityId: Int, model: WeatherInfoModel) {

        model.getWeatherInfo(cityId, object : RequestCompleteListener<WeatherInfoResponse> {
            override fun onRequestSuccess(data: WeatherInfoResponse) {

                // data formatting to show on UI
                val weatherData = WeatherData(
                    dateTime = data.dt.unixTimestampToDateTimeString(),
                    temperature = data.main.temp.kelvinToCelsius().toString(),
                    cityAndCountry = "${data.name}, ${data.sys.country}",
                    weatherConditionIconUrl = "http://openweathermap.org/img/w/${data.weather[0].icon}.png",
                    weatherConditionIconDescription = data.weather[0].description,
                    humidity = "${data.main.humidity}%",
                    pressure = "${data.main.pressure} mBar",
                    visibility = "${data.visibility / 1000.0} KM",
                    sunrise = data.sys.sunrise.unixTimestampToTimeString(),
                    sunset = data.sys.sunset.unixTimestampToTimeString()
                )

                progressBarLiveData.postValue(false)

                weatherInfoLiveData.postValue(weatherData)
            }

            override fun onRequestFailed(errorMessage: String) {
                progressBarLiveData.postValue(false) // hide progress bar
                weatherInfoFailureLiveData.postValue(errorMessage)
            }
        })
    }
}
