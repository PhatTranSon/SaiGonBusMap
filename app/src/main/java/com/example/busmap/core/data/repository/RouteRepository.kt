package com.example.busmap.core.data.repository

import com.example.busmap.core.data.datasource.BusRouteDataSource
import com.example.busmap.core.domain.models.BusRoute
import com.example.busmap.core.domain.models.BusStop

class RouteRepository(private val routeDataSource: BusRouteDataSource) {
    suspend fun getRoutes(busStop: BusStop) = routeDataSource.getRoutes(busStop)
    suspend fun getInfo(busRoute: BusRoute) = routeDataSource.getRouteInfo(busRoute)
    suspend fun getStopsOnRoute(busRoute: BusRoute) = routeDataSource.getStopsOnRoute(busRoute)
    suspend fun getRoutePolyline(busRoute: BusRoute) = routeDataSource.getRoutePolyline(busRoute)
}