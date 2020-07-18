package com.example.busmap.core.framework.db.implementation

import com.example.busmap.core.data.datasource.BusPathDataSource
import com.example.busmap.core.domain.models.BusNavigation
import com.example.busmap.core.domain.models.BusRoutePolyline
import com.example.busmap.core.domain.models.Coordinate
import com.example.busmap.core.framework.db.api.BusAPI
import com.example.busmap.core.framework.db.api.TrafficAPI
import com.example.busmap.core.framework.db.mappers.rest.BusPathMapper

class BusPathDataSourceImpl(private val busAPI : BusAPI,
                            private val trafficAPI: TrafficAPI,
                            private val busPathMapper: BusPathMapper
) : BusPathDataSource {
    override suspend fun getNavigation(startCoord: Coordinate, endCoord: Coordinate): List<BusNavigation> {
        val navigationResults = busAPI.getPath(
            startLat = startCoord.lat,
            startLng = startCoord.lng,
            endLat = endCoord.lat,
            endLng = endCoord.lng
        )

        return navigationResults.map {
            busPathMapper.fromEntity(it)
        }
    }

    override suspend fun getDirection(startLat : Double,
                                      startLng : Double,
                                      endLat : Double,
                                      endLng: Double) : BusRoutePolyline {
        return busPathMapper.fromDirectionEntity(
            trafficAPI.getDirection(
                startLat, startLng, endLat, endLng
            )
        )
    }
}