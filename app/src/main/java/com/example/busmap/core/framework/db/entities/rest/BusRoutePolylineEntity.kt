package com.example.busmap.core.framework.db.entities.rest

import com.google.gson.annotations.SerializedName

data class BusRoutePolylineEntity(
    @SerializedName("lat")
    val latitudes : List<Double>,
    @SerializedName("lng")
    val longitudes : List<Double>
)