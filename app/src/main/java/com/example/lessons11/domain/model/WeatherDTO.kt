package com.example.lessons11.domain.model

data class WeatherDTO(
    val fact: FactDTO?
) {
    operator fun get(i: Int) {

    }
}


data class FactDTO(
    val temp: Int?,
    val feels_like: Int?,
    val condition: String?
)


