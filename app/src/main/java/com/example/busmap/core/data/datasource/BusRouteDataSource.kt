package com.example.busmap.core.data.datasource

import com.example.busmap.core.domain.models.*

interface BusRouteDataSource {
    suspend fun getRoutes(busStop: BusStop) : List<BusRoute>
    suspend fun getRouteInfo(busRoute: BusRoute) : BusRouteInfo
    suspend fun getStopsOnRoute(busRoute: BusRoute) : List<BusStop>
    suspend fun getRouteVariation(busRoute: BusRoute) : List<BusRouteVariation>
    suspend fun getRoutePolyline(busRoute : BusRoute) : BusRoutePolyline
}