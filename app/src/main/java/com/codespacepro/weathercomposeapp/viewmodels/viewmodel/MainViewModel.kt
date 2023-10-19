package com.codespacepro.weathercomposeapp.viewmodels.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codespacepro.weathercomposeapp.model.Weather
import com.codespacepro.weathercomposeapp.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

class MainViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _myResponse: MutableLiveData<Response<Weather>> = MutableLiveData()
    private val _myForecastResponse: MutableLiveData<Response<Weather>> = MutableLiveData()

    val myForecastResponse: LiveData<Response<Weather>> = _myForecastResponse
    val myResponse: LiveData<Response<Weather>> = _myResponse

    fun getWeather(api: String, q: String) {
        viewModelScope.launch {
            val response = repository.getWeather(api, q)
            _myResponse.value = response
        }
    }

    fun getForecast(api: String, q: String) {
        viewModelScope.launch {
            val response = repository.getForecast(api, q)
            _myForecastResponse.value = response
        }
    }
}