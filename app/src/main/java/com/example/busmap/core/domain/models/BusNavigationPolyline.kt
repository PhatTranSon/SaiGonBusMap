package com.example.busmap.core.domain.models

data class BusNavigationPolyline(
    val pre : BusRoutePolyline,
    val main : BusRoutePolyline,
    val post : BusRoutePolyline
)