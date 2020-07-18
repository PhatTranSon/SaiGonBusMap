package com.example.busmap.core.framework.db.entities.rest

import com.google.gson.annotations.SerializedName

data class BusPathStopEntity(
    @SerializedName("Name")
    val name : String?,
    @SerializedName("Lat")
    val lat : Double,
    @SerializedName("Lng")
    val lng : Double,
    @SerializedName("Type")
    val type : Int
) {
    override fun toString(): String {
        return "BusPathStopEntity(name=$name, lat=$lat, lng=$lng, type=$type)"
    }
}