package com.example.busmap.core.framework.db.entities.rest

import com.google.gson.annotations.SerializedName

data class BusRouteVariationEntity(
    @SerializedName("EndStop")
    val endStop : String?,
    @SerializedName("StartStop")
    val startStop : String?,
    @SerializedName("RouteVarShortName")
    val name : String?,
    @SerializedName("Distance")
    val distance : Double,
    @SerializedName("RouteVarId")
    val id : Int
)