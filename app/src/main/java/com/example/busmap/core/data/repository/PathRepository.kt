package com.example.busmap.core.data.repository

import com.example.busmap.core.data.datasource.BusPathDataSource
import com.example.busmap.core.domain.models.BusNavigation
import com.example.busmap.core.domain.models.BusNavigationPolyline
import com.example.busmap.core.domain.models.BusRoutePolyline
import com.example.busmap.core.domain.models.Coordinate

class PathRepository(private val busPathDataSource: BusPathDataSource) {
    suspend fun getNavigation(startCoordinate: Coordinate, endCoordinate: Coordinate) =
        busPathDataSource.getNavigation(startCoordinate, endCoordinate)

    suspend fun getDirection(busNavigation: BusNavigation) : BusNavigationPolyline {
        //Get the direction object
        if (busNavigation.details.size >= 2) {
            //Get start and end segment
            val startSegment = busNavigation.details.first()
            val endSegment = busNavigation.details.last()

            //Get two polyline of start and end segment
            var startPolyline = BusRoutePolyline(emptyList())
            if (startSegment.isWalk) {
                startPolyline = busPathDataSource.getDirection(
                    startSegment.inLat,
                    startSegment.inLng,
                    startSegment.offLat,
                    startSegment.offLng
                )
            }

            var endPolyline = BusRoutePolyline(emptyList())
            if (endSegment.isWalk) {
                endPolyline = busPathDataSource.getDirection(
                    endSegment.inLat,
                    endSegment.inLng,
                    endSegment.offLat,
                    endSegment.offLng
                )
            }

            /*
            Log.i("SSEG", startSegment.toString())
            Log.i("ESEG", endSegment.toString())
            Log.i("START", startPolyline.coordinates.size.toString())
            Log.i("END", endPolyline.coordinates.size.toString())
             */

            //Create bus polyline and return
            return BusNavigationPolyline(
                startPolyline,
                BusRoutePolyline(busNavigation.coordinates),
                endPolyline
            )
        } else {
            return BusNavigationPolyline(
                BusRoutePolyline(emptyList()),
                BusRoutePolyline(busNavigation.coordinates),
                BusRoutePolyline(emptyList())
            )
        }
    }
}