package com.sarnava.covidchat.repository

import com.sarnava.covidchat.model.CovidResponse
import com.sarnava.covidchat.network.ApiClient
import com.sarnava.covidchat.network.ApiInterface
import retrofit2.Response
import com.sarnava.covidchat.extention.enqueue

object NetworkRepository {

    private var apiInterface = ApiClient.getClient().create(ApiInterface::class.java)

    fun getData(
        success: (Response<CovidResponse>) -> Unit,
        error: (Response<CovidResponse>) -> Unit,
        failure: (t: Throwable) -> Unit
    ) {
        val call = apiInterface.getData()
        call.enqueue(
            {
                success(it)
            },
            {
                error(it)
            },
            {
                failure(it)
            })
    }
}