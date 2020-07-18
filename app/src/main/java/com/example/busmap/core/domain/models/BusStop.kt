package com.example.busmap.core.domain.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BusStop (
    val code : String,
    val address : String,
    val lat : Double,
    val lng : Double,
    val name : String,
    val search : String,
    val status : String,
    val id : String,
    val type : String,
    val street : String,
    val ward : String,
    val zone : String,
    val routes : List<Route>
) : Parcelable {
    override fun toString(): String {
        return "BusStop(code='$code', address='$address', lat=$lat, lng=$lng, name='$name', search='$search', status='$status', id='$id', type='$type', street='$street', ward='$ward', zone='$zone', routes=$routes)"
    }
}