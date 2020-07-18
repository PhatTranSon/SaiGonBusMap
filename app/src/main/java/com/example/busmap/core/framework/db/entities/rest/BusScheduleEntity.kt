package com.example.busmap.core.framework.db.entities.rest

import com.google.gson.annotations.SerializedName

data class BusScheduleEntity(
    @SerializedName("d")
    val distance : Double,
    @SerializedName("v")
    val plate : String?,
    @SerializedName("t")
    val timeToDestination : Int
)