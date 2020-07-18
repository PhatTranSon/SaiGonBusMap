package com.example.busmap.core.framework.db.services

import com.example.busmap.core.framework.db.entities.rest.BusRouteInfoEntity
import com.example.busmap.core.framework.db.entities.rest.BusRoutePolylineEntity
import com.example.busmap.core.framework.db.entities.rest.BusRouteVariationEntity
import com.example.busmap.core.framework.db.entities.rest.BusStopRestEntity
import retrofit2.http.GET
import retrofit2.http.Path

interface BusRouteService {
    @GET("/businfo/getroutebyid/{routeId}")
    suspend fun getRouteInfo(@Path("routeId") routeId : String) : BusRouteInfoEntity

    @GET("/businfo/getstopsbyvar/{routeId}/{variationId}")
    suspend fun getStopsOnRoute(@Path("routeId") routeId : String,
                                @Path("variationId") variationId: Int) : List<BusStopRestEntity>

    @GET("/businfo/getvarsbyroute/{routeId}")
    suspend fun getRouteVariation(@Path("routeId") routeId: String) : List<BusRouteVariationEntity>

    @GET("/businfo/getpathsbyvar/{routeId}/{variationId}")
    suspend fun getRoutePolyline(@Path("routeId") routeId : String,
                                 @Path("variationId") variationId : Int) : BusRoutePolylineEntity
}