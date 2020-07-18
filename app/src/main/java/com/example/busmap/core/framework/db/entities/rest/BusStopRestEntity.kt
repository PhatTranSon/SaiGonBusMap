package com.example.busmap.core.framework.db.entities.rest

import com.google.gson.annotations.SerializedName


data class BusStopRestEntity(
    @SerializedName("StopId")
    val id : String?,
    @SerializedName("Code")
    val code : String?,
    @SerializedName("AddressNo")
    val address : String?,
    @SerializedName("Lat")
    val lat : Double,
    @SerializedName("Lng")
    val lng : Double,
    @SerializedName("Name")
    val name : String?,
    @SerializedName("Search")
    val search : String?,
    @SerializedName("Status")
    val status : String?,
    @SerializedName("StopType")
    val type : String?,
    @SerializedName("Street")
    val street : String?,
    @SerializedName("Ward")
    val ward : String?,
    @SerializedName("Zone")
    val zone : String?
)