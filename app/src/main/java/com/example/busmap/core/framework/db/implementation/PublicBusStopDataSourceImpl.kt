package com.example.busmap.core.framework.db.implementation

import com.example.busmap.core.data.datasource.PublicBusDataSource
import com.example.busmap.core.domain.models.BusStop
import com.example.busmap.core.domain.models.Coordinate
import com.example.busmap.core.framework.db.api.BusAPI
import com.example.busmap.core.framework.db.mappers.rest.BusStopMapper

class PublicBusStopDataSourceImpl(private val busAPI: BusAPI, private val mapper: BusStopMapper)
    : PublicBusDataSource {
    override suspend fun getAllStops(topLeft: Coordinate, bottomRight: Coordinate) : List<BusStop> {
        return busAPI.getAllBusStops(topLeft.lng, topLeft.lat, bottomRight.lng, bottomRight.lat)
            .map {
                mapper.fromRestToModel(it)
            }
    }
}