package com.example.busmap.core.framework.db.services

import com.example.busmap.core.framework.db.entities.rest.BusPathEntity
import retrofit2.http.GET
import retrofit2.http.Path

interface BusPathService {
    @GET("/pathfinding/getpathbystop/{startLat},{startLng}/{endLat},{endLng}/2")
    suspend fun findPath(@Path("startLat") startLat : Double,
                         @Path("startLng") startLng : Double,
                         @Path("endLat") endLat : Double,
                         @Path("endLng") endLng : Double) : List<BusPathEntity>
}