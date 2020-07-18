package com.example.busmap.core.data.datasource

import com.example.busmap.core.domain.models.BusStop
import com.example.busmap.core.domain.models.Coordinate

interface PublicBusDataSource {
    suspend fun getAllStops(topLeft : Coordinate, bottomRight : Coordinate) : List<BusStop>
}