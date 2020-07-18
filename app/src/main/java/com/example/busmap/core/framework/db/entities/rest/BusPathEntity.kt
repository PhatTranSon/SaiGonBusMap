package com.example.busmap.core.framework.db.entities.rest

import com.google.gson.annotations.SerializedName

data class BusPathEntity(
    @SerializedName("Title")
    val title : String?,
    @SerializedName("Desc")
    val description : String?,
    @SerializedName("detail")
    val detail : List<BusPathDetailsEntity>,
    @SerializedName("stops")
    val stops : List<BusPathStopEntity>,
    @SerializedName("coordRoute")
    val coordinates : Map<String, List<BusPathCoordinateEntity>>
) {
    override fun toString(): String {
        return "BusPathEntity(details=$detail, stops=$stops, coordinates=$coordinates)"
    }
}