package com.example.busmap.core.framework.db.mappers.rest

import com.example.busmap.core.domain.models.*
import com.example.busmap.core.framework.db.entities.rest.BusPathEntity
import com.example.busmap.core.framework.db.entities.google.DirectionEntity
import com.google.maps.android.PolyUtil

class BusPathMapper {
    fun fromEntity(busPathEntity: BusPathEntity) : BusNavigation {
        //Create list first
        val stops = arrayListOf<BusStop>()
        val coordinates = arrayListOf<Coordinate>()
        val details = arrayListOf<BusNavigationDetails>()

        //Populate list
        busPathEntity.stops.forEach {
            //Add element
            stops.add(
                BusStop(
                    name = it.name ?: "None",
                    lat = it.lat,
                    lng = it.lng,
                    search = "",
                    status = "",
                    id = "",
                    type = "",
                    street = "",
                    ward = "",
                    zone = "",
                    routes = emptyList(),
                    code = "",
                    address = ""
                )
            )
        }

        //Add bus path
        busPathEntity.coordinates.forEach {
            it.value.forEach { busPathCoordinateEntity ->
                coordinates.add(
                    Coordinate(
                        lat = busPathCoordinateEntity.lat,
                        lng = busPathCoordinateEntity.lng
                    )
                )
            }
        }

        //Add details
        busPathEntity.detail.forEach {
            details.add(
                BusNavigationDetails(
                    distance = it.distance,
                    fare = it.fare,
                    isWalk = !it.imageUrl.isNullOrBlank(),
                    inLat = it.inLat,
                    inLng = it.inLng,
                    offLat = it.offLat,
                    offLng = it.offLng,
                    inName = it.getInName ?: "None",
                    offName = it.getOffName ?: "None"
                )
            )
        }

        //Set fare
        val totalFare = details.map { it.fare }.reduceRight { i, acc -> acc + i }

        //Return
        return BusNavigation(
            fare = totalFare,
            title = busPathEntity.title ?: "None",
            description = busPathEntity.description ?: "None",
            details = details,
            stops = stops,
            coordinates = coordinates
        )
    }

    fun fromDirectionEntity(directionEntity: DirectionEntity) : BusRoutePolyline {
        //Get routes
        if (directionEntity.routes.isNotEmpty()) {
            //Get the first route
            val route = directionEntity.routes.first()

            //Convert its polyline encoded to list of lat and lng
            val decoded = PolyUtil.decode(route.polyline.points)

            //Convert to list of coordinates
            val coordinates = arrayListOf<Coordinate>()
            decoded.forEach {
                coordinates.add(Coordinate(it.latitude, it.longitude))
            }

            //Return object
            return BusRoutePolyline(coordinates)
        } else {
            return BusRoutePolyline(emptyList())
        }
    }
}