package com.example.busmap.core.framework.db.services

import com.example.busmap.core.framework.db.entities.rest.BusRouteEntity
import com.example.busmap.core.framework.db.entities.rest.BusStopRestEntity
import retrofit2.http.GET
import retrofit2.http.Path

interface BusStopService {
    @GET("/businfo/getstopsinbounds/{lng1}/{lat1}/{lng2}/{lat2}")
    suspend fun getAllStops(@Path("lng1") lng1 : Double,
                            @Path("lat1") lat1 : Double,
                            @Path("lng2") lng2 : Double,
                            @Path("lat2") lat2 : Double) : List<BusStopRestEntity>

    @GET("/prediction/predictbystopid/{stopId}")
    suspend fun getRoutes(@Path("stopId") stopId : String) : List<BusRouteEntity>
}