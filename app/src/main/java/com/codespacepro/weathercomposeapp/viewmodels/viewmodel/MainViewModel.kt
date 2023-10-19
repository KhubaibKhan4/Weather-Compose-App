package com.codespacepro.weathercomposeapp.viewmodels.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codespacepro.weathercomposeapp.model.Weather
import com.codespacepro.weathercomposeapp.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

class MainViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

     val myResponse: MutableLiveData<Response<Weather>> = MutableLiveData()
     val myForecastResponse: MutableLiveData<Response<Weather>> = MutableLiveData()


    fun getWeather(api: String, q: String) {
        viewModelScope.launch {
            val response = repository.getWeather(api, q)
            myResponse.value = response
        }
    }

    fun getForecast(api: String, q: String) {
        viewModelScope.launch {
            val response = repository.getForecast(api, q)
            myForecastResponse.value = response
        }
    }
}