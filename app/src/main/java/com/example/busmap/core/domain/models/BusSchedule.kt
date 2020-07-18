package com.example.busmap.core.domain.models

data class BusSchedule(
    val distance : Double,
    val plate : String,
    val timeToDestination : Int
) {
    override fun toString(): String {
        return "BusSchedule(distance=$distance, plate='$plate', timeToDestination=$timeToDestination)"
    }
}