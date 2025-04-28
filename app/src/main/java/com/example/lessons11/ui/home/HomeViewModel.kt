package com.example.lessons11.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lessons11.data.fake.PepositoryImpl
import com.example.lessons11.data.fake.Repository
import java.lang.Thread.sleep

// vie model слой который отвечает за логику
class HomeViewModel(
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(), // liveData которая помогает следить за AppsTate
    private val repositoryImpl: Repository = PepositoryImpl()
) : ViewModel() {

    fun getLiveData() = liveDataToObserve

    fun getWeatherFromLocalSource() = getDataFromLocalSource()

    fun getWeatherFromRemoteStorage() = getDataFromLocalSource()

    fun getWeather() = getDataFromLocalSource()


    private fun getDataFromLocalSource() {
        liveDataToObserve.value = AppState.Loading
        Thread {
            sleep(1000)
            liveDataToObserve.postValue(AppState.Success(repositoryImpl.getWeatherFromLocalStorage()))
        }.start()
    }
}