package com.example.busmap.core.framework.db.services

import com.example.busmap.core.framework.db.entities.google.DirectionEntity
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TrafficService {
    @GET("/maps/api/directions/json?mode=walking")
    suspend fun getDirection(@Query("origin") startLat : String,
                             @Query("destination") startLng : String,
                             @Query("key") key : String) : DirectionEntity
}