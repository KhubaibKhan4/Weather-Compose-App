package com.codespacepro.weathercomposeapp.repository

import com.codespacepro.weathercomposeapp.api.RetrofitInstance
import com.codespacepro.weathercomposeapp.model.Weather
import retrofit2.Response

class Repository {

    suspend fun getWeather(api: String, q: String): Response<Weather> {
        return RetrofitInstance.api.getWeather(api, q)
    }
}