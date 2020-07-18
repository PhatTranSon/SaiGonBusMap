package com.example.busmap.core.framework.db.entities.google

import com.google.gson.annotations.SerializedName

data class PolylineEntity(
    @SerializedName("points")
    val points : String?
) {
    override fun toString(): String {
        return "PolylineEntity(points=$points)"
    }
}