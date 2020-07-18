package com.example.busmap.core.framework.db.entities.google

import com.google.gson.annotations.SerializedName

data class DirectionEntity(
    @SerializedName("routes")
    val routes : List<RouteEntity>
) {
    override fun toString(): String {
        return "DirectionEntity(routes=$routes)"
    }
}