package com.codespacepro.weathercomposeapp.api

import com.codespacepro.weathercomposeapp.model.Weather
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("current.json")
    suspend fun getWeather(
        @Query("key") key: String,
        @Query("q") q: String
    ): Response<Weather>

}