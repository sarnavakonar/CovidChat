package com.sarnava.covidchat.model

data class CovidData(
    val TotalConfirmed: Int,
    val TotalDeaths: Int,
    val CountryCode: String?,
    val Country: String?
)