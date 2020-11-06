package com.sarnava.covidchat.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {

    private var retrofit: Retrofit? = null
    private const val limit = 10L

    fun getClient(): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl("https://api.covid19api.com")
                .addConverterFactory(GsonConverterFactory.create())
                .client(getOkHttpClient())
                .build()
        }
        return retrofit!!
    }

    private fun getOkHttpClient(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.connectTimeout(limit, TimeUnit.SECONDS)
        httpClient.readTimeout(limit, TimeUnit.SECONDS)
        httpClient.writeTimeout(limit, TimeUnit.SECONDS)
        httpClient.addInterceptor {
            val request = it.request()
                .newBuilder()
                .build()
            it.proceed(request)
        }
        return httpClient.build()
    }
}