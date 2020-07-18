package com.example.busmap.core.framework.db.implementation

import com.example.busmap.core.data.datasource.PersonalBusDataSource
import com.example.busmap.core.domain.models.BusStop
import com.example.busmap.core.framework.db.dao.BusStopDAO
import com.example.busmap.core.framework.db.mappers.rest.BusStopMapper

class PersonalBusStopDataSourceImpl(
    private val busStopDAO: BusStopDAO,
    private val busStopMapper: BusStopMapper
)
    : PersonalBusDataSource {
    override suspend fun getAllStops() = busStopDAO.getAll().map {
        busStopMapper.fromRoomToModel(it)
    }

    override suspend fun add(busStop: BusStop) = busStopDAO.add(
        busStopMapper.fromModelToRoom(busStop)
    )

    override suspend fun delete(busStop: BusStop) = busStopDAO.delete(busStop.id)
}