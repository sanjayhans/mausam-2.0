package com.panic1k.mausam20.weatherinfo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.panic1k.mausam20.weatherinfo.model.ModelCallback
import com.panic1k.mausam20.weatherinfo.model.WeatherInfoModel
import com.panic1k.mausam20.weatherinfo.model.repo.City
import com.panic1k.mausam20.weatherinfo.model.repo.WeatherData
import com.panic1k.mausam20.weatherinfo.model.repo.WeatherInfoResponse


class WeatherInfoViewModel : ViewModel() {
    /**
     * In our project, we have used different LiveData for success and failure.
     * both using a single LiveData. Another good approach may be handle errors in BaseActivity.
     */
    val cityListLiveData = MutableLiveData<List<City>>()
    val cityListFailureLiveData = MutableLiveData<String>()
    val weatherInfoLiveData = MutableLiveData<WeatherData>()
    val weatherInfoFailureLiveData = MutableLiveData<String>()

    /**we can inject the instance of Model in Constructor using dependency injection.
     */
    fun getCityList(model: WeatherInfoModel) {

        model.getCityList(object : ModelCallback<List<City>> {
            override fun onSuccess(data: List<City>) {
                cityListLiveData.postValue(data)
            }

            override fun onError(throwable: Throwable) {
                cityListFailureLiveData.postValue(throwable.localizedMessage)
            }
        })

    }

    /**we can inject the instance of Model in Constructor using dependency injection here
     */
    fun getWeatherInfo(cityId: Int, model: WeatherInfoModel) {

        model.getWeatherInfo(cityId, object : ModelCallback<WeatherInfoResponse> {
            override fun onSuccess(data: WeatherInfoResponse) {
                // TODO: convert WeatherInfoResponse to WeatherData object
                weatherInfoLiveData.postValue(WeatherData())
            }

            override fun onError(throwable: Throwable) {
                weatherInfoFailureLiveData.postValue(throwable.localizedMessage)
            }
        })
    }
}
