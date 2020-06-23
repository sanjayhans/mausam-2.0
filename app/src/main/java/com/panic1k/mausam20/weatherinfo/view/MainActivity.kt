package com.panic1k.mausam20.weatherinfo.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.panic1k.mausam20.R
import com.panic1k.mausam20.weatherinfo.model.WeatherInfoModel
import com.panic1k.mausam20.weatherinfo.model.WeatherInfoModelImpl
import com.panic1k.mausam20.weatherinfo.model.repo.City
import com.panic1k.mausam20.weatherinfo.viewmodel.WeatherInfoViewModel
import kotlinx.android.synthetic.main.layout_view_weather.*

class MainActivity : AppCompatActivity() {

    private val model: WeatherInfoModel = WeatherInfoModelImpl(this)
    private lateinit var viewModel: WeatherInfoViewModel
    private lateinit var cityList: List<City>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // initialize ViewModel
        viewModel = ViewModelProviders.of(this).get(WeatherInfoViewModel::class.java)

        // fetch city list when Activity open
        viewModel.getCityList(model)

        // observe city list success data
        viewModel.cityListLiveData.observe(this, Observer { cityList ->
            this.cityList = cityList
            Log.d("City", cityList.size.toString())
        })

        viewModel.weatherInfoFailureLiveData.observe(this, Observer { errorMessage ->
            Log.d("Weather Error", errorMessage)
        })

        // fetch data when button clicked
        btn_view_weather.setOnClickListener {
            val selectedCityId = cityList[spinner.selectedItemPosition].id
            viewModel.getWeatherInfo(selectedCityId, model)
        }
    }
}
