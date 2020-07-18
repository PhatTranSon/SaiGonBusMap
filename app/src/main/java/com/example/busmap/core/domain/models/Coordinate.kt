package com.example.busmap.core.domain.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Coordinate(
    val lat : Double,
    val lng : Double
) : Parcelable {
    override fun toString(): String {
        return "Coordinate(lat=$lat, lng=$lng)"
    }
}