package com.example.busmap.view.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.busmap.view.viewmodel.RouteViewModel
import com.example.busmap.core.data.repository.RouteRepository
import com.example.busmap.core.framework.db.api.BusAPI
import com.example.busmap.core.framework.db.implementation.BusRouteDataSourceImpl
import com.example.busmap.core.framework.db.mappers.rest.BusRouteMapper
import com.example.busmap.core.framework.db.mappers.rest.BusStopMapper
import java.lang.IllegalArgumentException

class RouteViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RouteViewModel::class.java)) {
            //Create repository
            val routeRepository = RouteRepository(BusRouteDataSourceImpl(BusAPI(),
                BusRouteMapper(),
                BusStopMapper()
            ))
            //Return view model
            return RouteViewModel(routeRepository) as T
        }
        throw IllegalArgumentException("Invalid ViewModel class")
    }
}