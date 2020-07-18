package com.example.busmap.core.data.repository

import com.example.busmap.core.data.datasource.PersonalBusDataSource
import com.example.busmap.core.data.datasource.PublicBusDataSource
import com.example.busmap.core.domain.models.BusStop
import com.example.busmap.core.domain.models.Coordinate

class BusRepository(private val publicBusDataSource : PublicBusDataSource,
                    private val personalBusDataSource: PersonalBusDataSource) {
    suspend fun getPublicBusStops(topLeft : Coordinate, bottomRight : Coordinate)
            = publicBusDataSource.getAllStops(topLeft, bottomRight)

    suspend fun getSavedBusStops() = personalBusDataSource.getAllStops()

    suspend fun add(busStop: BusStop) = personalBusDataSource.add(busStop)

    suspend fun delete(busStop: BusStop) = personalBusDataSource.delete(busStop)
}