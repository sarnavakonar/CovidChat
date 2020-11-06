package com.sarnava.covidchat.model

data class CovidResponse(
    var Global: CovidData,
    var Countries: MutableList<CovidData>
)