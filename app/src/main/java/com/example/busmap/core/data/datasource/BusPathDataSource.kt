package com.example.busmap.core.data.datasource

import com.example.busmap.core.domain.models.BusNavigation
import com.example.busmap.core.domain.models.BusRoutePolyline
import com.example.busmap.core.domain.models.Coordinate

interface BusPathDataSource {
    suspend fun getNavigation(startCoord : Coordinate, endCoord : Coordinate) : List<BusNavigation>
    suspend fun getDirection(startLat : Double,
                             startLng : Double,
                             endLat : Double,
                             endLng: Double) : BusRoutePolyline
}