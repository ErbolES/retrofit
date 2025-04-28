package com.example.lessons11.ui.home

import com.example.lessons11.domain.model.Weather
import com.example.lessons11.domain.model.WeatherDTO

sealed class AppState {
    data class Success(val weatherData: List<Weather>):AppState()
    data class Error(val error: Throwable):AppState()
    object Loading:AppState()
}