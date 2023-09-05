package com.codespacepro.weathercomposeapp.api

import com.codespacepro.weathercomposeapp.util.Constant.Companion.BASEURL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val api: WeatherApi by lazy {
        retrofit.create(WeatherApi::class.java)
    }
}