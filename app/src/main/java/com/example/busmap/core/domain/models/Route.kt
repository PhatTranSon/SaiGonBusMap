package com.example.busmap.core.domain.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Route(
    val id : String,
    val name : String,
    val number : String
) : Parcelable