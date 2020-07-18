package com.example.busmap.core.framework.db.entities.rest

import com.google.gson.annotations.SerializedName

data class BusPathCoordinateEntity(
    @SerializedName("Latitude")
    val lat : Double,
    @SerializedName("Longitude")
    val lng : Double
) {
    override fun toString(): String {
        return "BusPathCoordinateEntity(lat=$lat, lng=$lng)"
    }
}