package com.example.lessons11.data.fake

import com.example.lessons11.domain.model.Weather
import com.example.lessons11.domain.model.getCities

class PepositoryImpl: Repository {
    override fun getWeatherFromServer() = Weather()


    override fun getWeatherFromLocalStorage() = getCities()
}