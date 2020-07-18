package com.example.busmap.core.domain.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BusNavigationDetails(
    val distance : Int,
    val fare : Int,
    val isWalk : Boolean,
    val inLat : Double,
    val inLng : Double,
    val offLat : Double,
    val offLng : Double,
    val inName : String,
    val offName : String
) : Parcelable {
    override fun toString(): String {
        return "BusNavigationDetails(distance=$distance, fare=$fare, isWalk=$isWalk, inLat=$inLat, inLng=$inLng, offLat=$offLat, offLng=$offLng)"
    }
}