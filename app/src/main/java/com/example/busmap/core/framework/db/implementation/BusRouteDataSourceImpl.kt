package com.example.busmap.core.framework.db.implementation

import com.example.busmap.core.data.datasource.BusRouteDataSource
import com.example.busmap.core.domain.models.*
import com.example.busmap.core.framework.db.api.BusAPI
import com.example.busmap.core.framework.db.mappers.rest.BusRouteMapper
import com.example.busmap.core.framework.db.mappers.rest.BusStopMapper

class BusRouteDataSourceImpl(private val busAPI: BusAPI,
                             private val busRouteMapper: BusRouteMapper,
                             private val busStopMapper: BusStopMapper
)
    : BusRouteDataSource {
    override suspend fun getRoutes(busStop: BusStop): List<BusRoute> {
        //Get the id from bus stop
        val id = busStop.id

        //Get the entity object
        return busAPI.getRoutes(id).map {
            busRouteMapper.fromEntity(it)
        }
    }

    override suspend fun getRouteInfo(busRoute: BusRoute) : BusRouteInfo {
        val info = busAPI.getRouteInfo(busRoute.id)
        return busRouteMapper.fromInfoEntity(info)
    }

    override suspend fun getStopsOnRoute(busRoute: BusRoute): List<BusStop> {
        //Get the variations
        val variations = getRouteVariation(busRoute)

        //Check if not empty
        return if (variations.isNotEmpty()) {
            //Get the first variation id
            val stops = busAPI.getStopsThroughRoute(busRoute.id, variations[0].id)
            stops.map {
                busStopMapper.fromRestToModel(it)
            }
        } else {
            emptyList()
        }
    }

    override suspend fun getRouteVariation(busRoute: BusRoute): List<BusRouteVariation> {
        val variations = busAPI.getRouteVariation(busRoute.id)
        return variations.map {
            busRouteMapper.fromVariationEntity(it)
        }
    }

    override suspend fun getRoutePolyline(busRoute: BusRoute): BusRoutePolyline {
        //Get the variations
        val variations = getRouteVariation(busRoute)

        //Check if not empty
        return if (variations.isNotEmpty()) {
            //Get the first variation id
            val result = busAPI.getRoutePolyline(busRoute.id, variations[0].id)
            busRouteMapper.fromPolylineEntity(result)
        } else {
            BusRoutePolyline(emptyList())
        }
    }
}