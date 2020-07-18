package com.example.busmap.core.domain.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BusNavigation(
    val fare : Int,
    val title : String,
    val description : String,
    val details : List<BusNavigationDetails>,
    val stops : List<BusStop>,
    val coordinates : List<Coordinate>
) : Parcelable {
    override fun toString(): String {
        return "BusNavigation(fare=$fare, title='$title', description='$description', details=$details, stops=$stops, coordinates=$coordinates)"
    }
}