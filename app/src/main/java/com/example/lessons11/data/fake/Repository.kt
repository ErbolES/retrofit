package com.example.lessons11.data.fake

import com.example.lessons11.domain.model.Weather

interface Repository {
    fun getWeatherFromServer(): Weather
    fun getWeatherFromLocalStorage():List<Weather>
}