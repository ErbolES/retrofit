package com.example.lessons11.presentation.ui

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.lessons11.domain.model.Weather
import com.example.lessons11.databinding.FragmentDetailsBinding
import com.example.lessons11.presentation.viewmodel.DetailsViewModel
import com.example.lessons11.ui.home.AppState

class DetailsFragment : Fragment() {


    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var weatherBundle: Weather

    val viewModel: DetailsViewModel by lazy {
        ViewModelProvider(this).get(DetailsViewModel::class.java)
    }
    companion object {
        const val  BUNDLE_NAME = "WEATHER"
        fun newInstance(bundle: Bundle): DetailsFragment {
            val fragment = DetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        weatherBundle = arguments?.getParcelable(BUNDLE_NAME) ?: Weather()
        binding.mainView.visibility = View.GONE
        binding.loadingLayout.visibility = View.VISIBLE
        val MAIN_LINK = "https://api.weather.yandex.ru/v2/forecast?"
        viewModel.detailsLiveData.observe(viewLifecycleOwner, Observer {
            displayWeather(it)
        })
        viewModel.getWeatherFromRemoteSource(weatherBundle.city.lat.toDouble(),weatherBundle.city.lon.toDouble())
    }


    private fun displayWeather(appState: AppState) {
        with(binding) {
            when (appState) {
                is AppState.Success -> {
                    binding.mainView.visibility = View.VISIBLE
                    binding.loadingLayout.visibility = View.GONE
                    setWeather(appState.weatherData[0])
                }
                is AppState.Loading -> {
                    binding.mainView.visibility = View.GONE
                    binding.loadingLayout.visibility = View.VISIBLE
                }
                is AppState.Error -> {
                    binding.mainView.visibility = View.VISIBLE
                    binding.loadingLayout.visibility = View.GONE
                }
            }
        }
    }

    private fun setWeather(weather: Weather){
        val city = weatherBundle.city
        binding.cityName.text = city.city
        binding.cityCoordinates.text = String.format(
            city.lat.toString(),
            city.lon.toString()
        )
        binding.temperatureValue.text = weather.temperature.toString()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /*
    private fun getWeather(){
        binding.mainView.visibility = View.GONE
        binding.loadingLayout.visibility = View.VISIBLE

        val client = OkHttpClient() // создаем клиент
        val builder:Request.Builder = Request.Builder() // создаем строителя запросов
        builder.header("X-Yandex-Weather-Key",YOUR_API_KEY,)// cоздаем заголовок запроса
        builder.url("https://api.weather.yandex.ru/v2/forecast?lat=${weatherBundle.city.lat}&lon=${weatherBundle.city.lon}")
        val request: Request = builder.build() // создаем запрос
        val call: Call = client.newCall(request) // Ставим в очередь
        call.enqueue(object :Callback{
            val handler: Handler = Handler(Looper.getMainLooper())
            override fun onFailure(call: Call, e: IOException) {

            }

            override fun onResponse(call: Call, response: Response) {
               val serverResponse:String? = response.body()?.string()
                if (response.isSuccessful && serverResponse != null){
                    handler.post{
                        displayWeather(
                            Gson().fromJson(serverResponse, WeatherDTO::class.java)
                        )
                    }
                }
                else{

                }
            }
        })
    }


    @RequiresApi(Build.VERSION_CODES.N)
    private fun loadWeather() {
        try {
            val uri =
                URL("https://api.weather.yandex.ru/v2/forecast?lat=${weatherBundle.city.lat}&lon=${weatherBundle.city.lon}")
            val handler = Handler(Looper.getMainLooper())
            Thread(Runnable {
                lateinit var urlConnection: HttpsURLConnection
                try {
                    urlConnection = uri.openConnection() as HttpsURLConnection
                    urlConnection.requestMethod = "GET"
                    urlConnection.addRequestProperty(
                        "X-Yandex-Weather-Key",
                        YOUR_API_KEY
                    )
                    urlConnection.readTimeout = 10000
                    val bufferedReader =
                        BufferedReader(InputStreamReader(urlConnection.inputStream))
// преобразование ответа от сервера (JSON) в модель данных (WeatherDTO)
                    val weatherDTO: WeatherDTO =
                        Gson().fromJson(getLines(bufferedReader),
                            WeatherDTO::class.java)
                    handler.post { displayWeather(weatherDTO) }
                } catch (e: Exception) {
                    Log.e("", "Fail connection", e)
                    e.printStackTrace()
//Обработка ошибки
                } finally {
                    urlConnection.disconnect()
                }
            }).start()
        } catch (e: MalformedURLException) {
            Log.e("", "Fail URI", e)
            e.printStackTrace()
//Обработка ошибки
        }
    }


    @RequiresApi(Build.VERSION_CODES.N)
    private fun getLines(reader: BufferedReader): String {
        return reader.lines().collect(Collectors.joining("\n"))
    }
     */
}