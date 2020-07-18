package com.example.busmap.core.framework.db.entities.rest

import com.google.gson.annotations.SerializedName

data class BusRouteEntity(
    @SerializedName("vN")
    val from : String?,
    @SerializedName("v")
    val fromId : String?,
    @SerializedName("sN")
    val to : String?,
    @SerializedName("s")
    val toId : String?,
    @SerializedName("r")
    val id : String?,
    @SerializedName("arrs")
    val schedules : List<BusScheduleEntity>,
    @SerializedName("rN")
    val name : String?,
    @SerializedName("rNo")
    val number : String?
)