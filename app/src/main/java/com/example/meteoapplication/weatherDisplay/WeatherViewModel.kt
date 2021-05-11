package com.example.meteoapplication.weatherDisplay

import androidx.lifecycle.*
import com.example.meteoapplication.repository.WeatherRepository
import com.example.meteoapplication.models.CityWeather
import com.example.meteoapplication.utils.Status
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

class WeatherViewModel : ViewModel() {

    private val repository = WeatherRepository()

    private val _listCitiesToDisplay = MutableLiveData<MutableList<CityWeather>>(mutableListOf())
    val listCitiesToDisplay: LiveData<MutableList<CityWeather>>
        get() = _listCitiesToDisplay

    private var _gettingDataStatus = MutableLiveData(Status.LOADING)
    val gettingDataStatus: LiveData<Status>
        get() = _gettingDataStatus

    private var _loadingSentence = MutableLiveData("")
    val loadingSentence: LiveData<String>
        get() = _loadingSentence


    private var _progress = MutableLiveData(0)
    val progress: LiveData<Int>
        get() = _progress

    private val listCityIds = listOf(
        "2983990", // Rennes
        "2988507", // Paris
        "2990969", // Nantes
        "3031582", // Bordeaux
        "2996944", // Lyon
    )

    private val listLoadingSentences = listOf(
        "Nous téléchargeons les données…",
        "C’est presque fini…",
        "Plus que quelques secondes avant d’avoir le résultat…"
    )

    init {
        Timber.i("test")
        getCities()
        fillLoadingSentence()
    }


    private fun getCities() {
        viewModelScope.launch {
            _gettingDataStatus.value = Status.LOADING
            _listCitiesToDisplay.value!!.clear()
            _progress.value = 0
            Timber.i("entree getCities")
            listCityIds.forEach lit@{
                val item = kotlin.runCatching { repository.getWeatherFromIdCitiesList(it) }
                item.onSuccess { cityWeather ->
                    Timber.i("cityWeather = $cityWeather")
                    delay(1500L)
                    _listCitiesToDisplay.value = _listCitiesToDisplay.value.also {
                        it?.add(cityWeather)
                    }
                    _progress.value = progress.value!! + 25

                }.onFailure {
                    Timber.e("ERROR: $it")
                    _gettingDataStatus.value = Status.ERROR
                    return@lit
                }
            }
            if (gettingDataStatus.value != Status.ERROR) {
                _gettingDataStatus.value = Status.SUCCESS
            }
        }
    }

    private fun fillLoadingSentence() {
        viewModelScope.launch {
            while (gettingDataStatus.value == Status.LOADING) {
                for (sentence in listLoadingSentences) {
                    _loadingSentence.value = sentence
                    delay(3000L)
                }
            }
        }
    }

    fun onClickGetData() {
        getCities()
    }


    //    fun getCities() = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            val listCitiesWeather = mutableListOf<CityWeather>()
//            listCityIds.forEach {
//                listCitiesWeather.add(repository.getWeatherFromIdCitiesList(it))
//            }
//            emit(Resource.success(data = listCitiesWeather))
//        } catch (exception: Exception) {
//            emit(Resource.error(data = null, msg = exception.message ?: "Error Occurred!"))
//        }
//    }

}