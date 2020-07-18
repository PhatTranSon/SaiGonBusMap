package com.example.busmap.core.framework.db.entities.google

import com.google.gson.annotations.SerializedName

data class RouteEntity (
    @SerializedName("overview_polyline")
    val polyline : PolylineEntity
) {
    override fun toString(): String {
        return "RouteEntity(polyline='$polyline')"
    }
}