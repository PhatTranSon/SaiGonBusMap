package com.example.busmap.core.framework.db.mappers.rest

import com.example.busmap.core.domain.models.*
import com.example.busmap.core.framework.db.entities.rest.BusRouteEntity
import com.example.busmap.core.framework.db.entities.rest.BusRouteInfoEntity
import com.example.busmap.core.framework.db.entities.rest.BusRoutePolylineEntity
import com.example.busmap.core.framework.db.entities.rest.BusRouteVariationEntity

class BusRouteMapper {
    fun fromEntity(busRouteEntity: BusRouteEntity) : BusRoute {
        //Convert schedule first
        val schedules =
            busRouteEntity.schedules.map {
                BusSchedule(
                    distance = it.distance,
                    plate = it.plate ?: "None",
                    timeToDestination = it.timeToDestination
                )
            }

        //Then convert the entity
        return BusRoute(
            from = busRouteEntity.from ?: "None",
            fromId = busRouteEntity.fromId ?: "None",
            to = busRouteEntity.to ?: "None",
            toId = busRouteEntity.toId ?: "None",
            id = busRouteEntity.id ?: "None",
            schedules = schedules,
            name = busRouteEntity.name ?: "None",
            number = busRouteEntity.number ?: "None"
        )
    }

    fun toEntity(busRoute : BusRoute) =
        BusRouteEntity(
            from = busRoute.from,
            fromId = busRoute.fromId,
            to = busRoute.to,
            toId = busRoute.toId,
            id = busRoute.id,
            name = busRoute.name,
            schedules = emptyList(),
            number = busRoute.number
        )

    fun fromInfoEntity(busRouteInfoEntity: BusRouteInfoEntity) = BusRouteInfo(
        id = busRouteInfoEntity.id,
        number = busRouteInfoEntity.number ?: "None",
        type = busRouteInfoEntity.type ?: "None",
        distance = busRouteInfoEntity.distance,
        company = busRouteInfoEntity.company ?: "None",
        timeOfTrip = busRouteInfoEntity.timeOfTrip ?: "None",
        timeOfWait = busRouteInfoEntity.timeOfWait ?: "None",
        operationTime = busRouteInfoEntity.operationTime ?: "None",
        numberOfSeat = busRouteInfoEntity.numberOfSeat ?: "None",
        outBound = busRouteInfoEntity.outBound ?: "None",
        inBound = busRouteInfoEntity.inBound ?: "None",
        name = busRouteInfoEntity.name ?: "None"
    )

    fun toInfoEntity(busRouteInfo: BusRouteInfo) =
        BusRouteInfoEntity(
            id = busRouteInfo.id,
            number = busRouteInfo.number,
            type = busRouteInfo.type,
            distance = busRouteInfo.distance,
            company = busRouteInfo.company,
            timeOfTrip = busRouteInfo.timeOfTrip,
            timeOfWait = busRouteInfo.timeOfWait,
            operationTime = busRouteInfo.operationTime,
            numberOfSeat = busRouteInfo.numberOfSeat,
            outBound = busRouteInfo.outBound,
            inBound = busRouteInfo.inBound,
            name = busRouteInfo.name
        )

    fun fromVariationEntity(busRouteVariationEntity: BusRouteVariationEntity) = BusRouteVariation(
        endStop = busRouteVariationEntity.endStop ?: "None",
        startStop = busRouteVariationEntity.startStop ?: "None",
        distance = busRouteVariationEntity.distance,
        id = busRouteVariationEntity.id,
        name = busRouteVariationEntity.name ?: "None"
    )

    fun fromPolylineEntity(busRoutePolylineEntity: BusRoutePolylineEntity) : BusRoutePolyline {
        //Create a coordinate vector
        val coordinates = arrayListOf<Coordinate>()

        //Append
        for (i in busRoutePolylineEntity.latitudes.indices) {
            coordinates.add(
                Coordinate(busRoutePolylineEntity.latitudes[i],
                    busRoutePolylineEntity.longitudes[i])
            )
        }

        //Shape into model
        return BusRoutePolyline(coordinates)
    }
}