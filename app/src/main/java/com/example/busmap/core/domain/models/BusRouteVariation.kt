package com.example.busmap.core.domain.models

data class BusRouteVariation(
    val endStop : String,
    val startStop : String,
    val name : String,
    val distance : Double,
    val id : Int
)