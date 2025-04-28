package com.example.lessons11.data.repository

import com.example.lessons11.domain.model.WeatherDTO
import com.example.lessons11.remote.RemoteDataSource
import retrofit2.Callback

class DetailsRepositoryImpl(
    private val remoteDataSource: RemoteDataSource = RemoteDataSource()
) : DetailsRepository {

    override fun getWeatherDetailsFromServer(
        lat: Double,
        lon: Double,
        callback: Callback<WeatherDTO>
    ) {
        remoteDataSource.getWeatherDetails(lat, lon, callback)
    }
}
