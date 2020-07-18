package com.example.busmap.core.framework.db.entities.rest

import com.google.gson.annotations.SerializedName

data class BusRouteInfoEntity (
    @SerializedName("RouteId")
    val id : Int,
    @SerializedName("RouteNo")
    val number : String?,
    @SerializedName("Type")
    val type : String?,
    @SerializedName("Distance")
    val distance : Double,
    @SerializedName("Orgs")
    val company : String?,
    @SerializedName("TimeOfTrip")
    val timeOfTrip : String?,
    @SerializedName("Headway")
    val timeOfWait : String?,
    @SerializedName("OperationTime")
    val operationTime : String?,
    @SerializedName("NumOfSeats")
    val numberOfSeat : String?,
    @SerializedName("OutBoundDescription")
    val outBound : String?,
    @SerializedName("InBoundDescription")
    val inBound : String?,
    @SerializedName("RouteName")
    val name : String?
)