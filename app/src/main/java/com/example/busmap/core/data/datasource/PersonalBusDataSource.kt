package com.example.busmap.core.data.datasource

import com.example.busmap.core.domain.models.BusStop

interface PersonalBusDataSource {
    suspend fun getAllStops() : List<BusStop>
    suspend fun add(busStop: BusStop) : Long
    suspend fun delete(busStop: BusStop)
}