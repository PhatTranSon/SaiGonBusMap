package com.example.busmap.core.domain.models

data class BusRoutePolyline(
    val coordinates : List<Coordinate>
) {
    override fun toString(): String {
        return "BusRoutePolyline(coordinates=$coordinates)"
    }
}