package com.example.busmap.core.framework.db.mappers.rest

import com.example.busmap.core.domain.models.BusStop
import com.example.busmap.core.framework.db.entities.rest.BusStopRestEntity
import com.example.busmap.core.framework.db.entities.room.BusStopRoomEntity

class BusStopMapper {
    fun fromRestToModel(busStopRestEntity: BusStopRestEntity) = BusStop(
        address = busStopRestEntity.address ?: "None",
        lat = busStopRestEntity.lat,
        lng = busStopRestEntity.lng,
        name = busStopRestEntity.name ?: "None",
        search = busStopRestEntity.search ?: "None",
        status = busStopRestEntity.status ?: "None",
        type = busStopRestEntity.type ?: "None",
        street = busStopRestEntity.street ?: "None",
        ward = busStopRestEntity.ward ?: "None",
        zone = busStopRestEntity.zone ?: "None",
        code = busStopRestEntity.code ?: "None",
        id = busStopRestEntity.id ?: "None",
        routes = listOf()
    )

    fun fromRoomToModel(busStopRoomEntity: BusStopRoomEntity) = BusStop(
        address = busStopRoomEntity.address,
        name = busStopRoomEntity.name,
        search = busStopRoomEntity.search,
        status = busStopRoomEntity.status,
        type =  busStopRoomEntity.type,
        street = busStopRoomEntity.street,
        ward = busStopRoomEntity.ward,
        zone = busStopRoomEntity.zone,
        code = busStopRoomEntity.code,
        id = busStopRoomEntity.restId,
        lat = busStopRoomEntity.lat,
        lng = busStopRoomEntity.lng,
        routes = listOf()
    )

    fun fromModelToRoom(busStop: BusStop) = BusStopRoomEntity(
        name = busStop.name,
        code = busStop.code,
        restId = busStop.id,
        address = busStop.address,
        search = busStop.search,
        status = busStop.status,
        type = busStop.type,
        street = busStop.street,
        ward = busStop.ward,
        zone = busStop.zone,
        lat = busStop.lat,
        lng = busStop.lng,
        uid = 0
    )
}