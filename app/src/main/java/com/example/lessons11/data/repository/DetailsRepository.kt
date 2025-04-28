package com.example.lessons11.data.repository

import com.example.lessons11.domain.model.WeatherDTO
import retrofit2.Callback

interface DetailsRepository {
    fun getWeatherDetailsFromServer(
        lat: Double,
        lon: Double,
        callback: Callback<WeatherDTO>
    )
}
