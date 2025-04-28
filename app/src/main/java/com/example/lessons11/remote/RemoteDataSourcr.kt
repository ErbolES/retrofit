package com.example.lessons11.remote

import com.example.lessons11.data.repository.BuildConfig
import com.example.lessons11.domain.model.WeatherDTO
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.GsonBuilder

class RemoteDataSource {

    private val weatherApi: WeatherAPI = Retrofit.Builder()
        .baseUrl("https://api.weather.yandex.ru/")
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .build()
        .create(WeatherAPI::class.java)

    fun getWeatherDetails(
        lat: Double,
        lon: Double,
        callback: Callback<WeatherDTO>
    ) {
        weatherApi.getWeather(BuildConfig.WEATHER_API_KEY, lat, lon).enqueue(callback)
    }
}
