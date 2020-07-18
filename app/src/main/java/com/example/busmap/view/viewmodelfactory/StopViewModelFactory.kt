package com.example.busmap.view.viewmodelfactory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.busmap.core.data.repository.BusRepository
import com.example.busmap.view.viewmodel.StopViewModel
import com.example.busmap.core.data.repository.RouteRepository
import com.example.busmap.core.framework.db.api.BusAPI
import com.example.busmap.core.framework.db.database.BusStopDatabase
import com.example.busmap.core.framework.db.implementation.BusRouteDataSourceImpl
import com.example.busmap.core.framework.db.implementation.PersonalBusStopDataSourceImpl
import com.example.busmap.core.framework.db.implementation.PublicBusStopDataSourceImpl
import com.example.busmap.core.framework.db.mappers.rest.BusRouteMapper
import com.example.busmap.core.framework.db.mappers.rest.BusStopMapper
import java.lang.IllegalArgumentException

class StopViewModelFactory(private val context : Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StopViewModel::class.java)) {
            //Create mappers and api
            val routeMapper = BusRouteMapper()
            val stopMapper = BusStopMapper()
            val api = BusAPI()

            //Create room db
            val busRoomDatabase = BusStopDatabase(context)

            //Create repositories
            val routeRepository = RouteRepository(
                BusRouteDataSourceImpl(
                    api,
                    routeMapper,
                    stopMapper
                )
            )

            val stopRepository = BusRepository(
                PublicBusStopDataSourceImpl(
                    api,
                    stopMapper
                ),
                PersonalBusStopDataSourceImpl(
                    busRoomDatabase.getDao(),
                    stopMapper
                )
            )

            //Return the view model
            return StopViewModel(routeRepository, stopRepository) as T
        }
        throw IllegalArgumentException("Invalid ViewModel class")
    }

}