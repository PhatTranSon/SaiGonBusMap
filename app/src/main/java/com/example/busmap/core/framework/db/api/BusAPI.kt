package com.example.busmap.core.framework.db.api

import com.example.busmap.core.framework.db.services.BusPathService
import com.example.busmap.core.framework.db.services.BusRouteService
import com.example.busmap.core.framework.db.services.BusStopService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BusAPI {
    companion object {
        private const val BASE_URL = "http://apicms.ebms.vn"
    }
    private val busStopService : BusStopService
    private val busRouteService : BusRouteService
    private val busPathService : BusPathService

    init {
        //Create retrofit instance
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        //Create service instances
        busStopService = retrofit.create(BusStopService::class.java)
        busRouteService = retrofit.create(BusRouteService::class.java)
        busPathService = retrofit.create(BusPathService::class.java)
    }
    
    suspend fun getAllBusStops(lng1 : Double, lat1 : Double, lng2 : Double, lat2 : Double) =
        busStopService.getAllStops(lng1, lat1, lng2, lat2)

    suspend fun getRoutes(stopId : String) = busStopService.getRoutes(stopId)

    suspend fun getRouteInfo(routeId : String) = busRouteService.getRouteInfo(routeId)

    suspend fun getStopsThroughRoute(routeId : String, variationId: Int) = busRouteService.getStopsOnRoute(routeId, variationId)

    suspend fun getRouteVariation(routeId : String) = busRouteService.getRouteVariation(routeId)

    suspend fun getRoutePolyline(routeId : String, variationId : Int) = busRouteService.getRoutePolyline(routeId, variationId)

    suspend fun getPath(startLat : Double, startLng : Double, endLat : Double, endLng : Double) = busPathService.findPath(
        startLat,
        startLng,
        endLat,
        endLng
    )
}