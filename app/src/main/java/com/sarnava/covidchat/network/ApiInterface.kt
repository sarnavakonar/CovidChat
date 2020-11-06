package com.sarnava.covidchat.network

import com.sarnava.covidchat.model.CovidResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    @GET("/summary")
    fun getData(): Call<CovidResponse>
}