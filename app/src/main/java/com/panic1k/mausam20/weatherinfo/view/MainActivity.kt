package com.panic1k.mausam20.weatherinfo.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.panic1k.mausam20.R
import com.panic1k.mausam20.utils.convertToListOfCityName
import com.panic1k.mausam20.weatherinfo.model.WeatherInfoModel
import com.panic1k.mausam20.weatherinfo.model.WeatherInfoModelImpl
import com.panic1k.mausam20.weatherinfo.model.repo.City
import com.panic1k.mausam20.weatherinfo.viewmodel.WeatherInfoViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_sunrise_sunset.*
import kotlinx.android.synthetic.main.layout_view_weather.*
import kotlinx.android.synthetic.main.layout_weather_additional_info.*
import kotlinx.android.synthetic.main.layout_weather_info.*

class MainActivity : AppCompatActivity() {

    private val model: WeatherInfoModel = WeatherInfoModelImpl(this)
    private lateinit var viewModel: WeatherInfoViewModel
    private var cityList: MutableList<City> = mutableListOf()

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
            val arrayAdapter = ArrayAdapter(
                this,
                R.layout.support_simple_spinner_dropdown_item,
                cityList.convertToListOfCityName()
            )
            spinner.adapter = arrayAdapter
        })

        // observe city list fetching failure
        viewModel.weatherInfoFailureLiveData.observe(this, Observer { errorMessage ->
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
        })

        // observe progress bar show/hide
        viewModel.progressBarLiveData.observe(this, Observer { isShowLoader ->
            if (isShowLoader)
                progressBar.visibility = View.VISIBLE
            else
                progressBar.visibility = View.GONE
        })

        // observe weather info fetching success
        viewModel.weatherInfoLiveData.observe(this, Observer { weatherData ->
            Log.e("Weather Data", weatherData.toString())
            output_group.visibility = View.VISIBLE
            tv_error_message.visibility = View.GONE

            tv_date_time?.text = weatherData.dateTime
            tv_temperature?.text = weatherData.temperature
            tv_city_country?.text = weatherData.cityAndCountry
            Glide.with(this).load(weatherData.weatherConditionIconUrl).into(iv_weather_condition)
            tv_weather_condition?.text = weatherData.weatherConditionIconDescription

            tv_humidity_value?.text = weatherData.humidity
            tv_pressure_value?.text = weatherData.pressure
            tv_visibility_value?.text = weatherData.visibility

            tv_sunrise_time?.text = weatherData.sunrise
            tv_sunset_time?.text = weatherData.sunset
        })

        // observe weather info fetching failure
        viewModel.weatherInfoFailureLiveData.observe(this, Observer { errorMessage ->
            Log.e("Weather Error", errorMessage)
            output_group.visibility = View.GONE
            tv_error_message.visibility = View.VISIBLE
            tv_error_message.text = errorMessage
        })
        // fetch data when button clicked
        btn_view_weather.setOnClickListener {
            val selectedCityId = cityList[spinner.selectedItemPosition].id
            viewModel.getWeatherInfo(selectedCityId, model)
        }
    }
}
