package com.example.busmap.core.framework.db.entities.rest

import com.google.gson.annotations.SerializedName

data class BusPathDetailsEntity(
    @SerializedName("Distance")
    val distance : Int,
    @SerializedName("Fare")
    val fare : Int,
    @SerializedName("ImageUrl")
    val imageUrl : String?,
    @SerializedName("GetInLat")
    val inLat : Double,
    @SerializedName("GetInLng")
    val inLng : Double,
    @SerializedName("GetOffLat")
    val offLat : Double,
    @SerializedName("GetOffLng")
    val offLng : Double,
    @SerializedName("GetIn")
    val getInName : String?,
    @SerializedName("GetOff")
    val getOffName : String?
) {
    override fun toString(): String {
        return "BusPathDetailsEntity(distance=$distance, fare=$fare, imageUrl=$imageUrl, inLat=$inLat, inLng=$inLng, offLat=$offLat, offLng=$offLng)"
    }
}