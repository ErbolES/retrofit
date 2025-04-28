package com.example.lessons11.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lessons11.domain.model.WeatherDTO
import com.example.lessons11.data.repository.DetailsRepository
import com.example.lessons11.data.repository.DetailsRepositoryImpl
import com.example.lessons11.ui.home.AppState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
private const val SERVER_ERROR = "Ошибка сервера"
private const val REQUEST_ERROR = "Ошибка запроса на сервер"
private const val CORRUPTED_DATA = "Неполные данные"
class DetailsViewModel(
    val detailsLiveData: MutableLiveData<AppState> = MutableLiveData(),
    private val detailsRepositoryImpl: DetailsRepositoryImpl = DetailsRepositoryImpl(
        RemoteDataSource()
    ),
    private val historyRepository:LocalRepository = LocalRepositoryImpl(getHistoryDao())

): ViewModel() {


    fun getWeatherDetailsFromServer(lat:Double,lon:Double){
        detailsLiveData.value = AppState.Loading
        detailsRepositoryImpl.GetWeatherDetailsFromServer(lat = lat, lon = lon, callback)
    }

    fun saveCituToDB(weather: Weather){
        historyRepository.saveEntity(weather)
    }

    private val callback = object :retrofit2.Callback<WeatherDTO>{
        override fun onResponse(
            call: retrofit2.Call<WeatherDTO>,
            response: retrofit2.Response<WeatherDTO>
        ) {
            val serverResponse: WeatherDTO? = response.body()
            detailsLiveData.postValue(
                if (response.isSuccessful && serverResponse!=null){
                    checkResponse(serverResponse)
                } else{
                    AppState.Error(Throwable(SERVER_ERROR))
                }
            )
        }

        override fun onFailure(call: retrofit2.Call<WeatherDTO>, t: Throwable) {
        }
    }
    private fun checkResponse(serverResponse:WeatherDTO):AppState{
        val fact = serverResponse.fact
        return if(fact  == null|| fact.temp == null){
            AppState.Error(Throwable(CORRUPTED_DATA))
        }else{
            AppState.Success(convertDtoToModel(serverResponse))
        }
    }

    fun convertDtoToModel(weatherDTO: WeatherDTO):List<Weather>{
        val fact:FactDTO = weatherDTO.fact!!
        return listOf(Weather(getDefault(), fact.temp!!))
    }
}
