package com.example.busmap.view.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.busmap.core.data.repository.RouteRepository
import com.example.busmap.core.domain.models.BusRoute
import com.example.busmap.core.domain.models.BusRouteInfo
import com.example.busmap.core.domain.models.BusRoutePolyline
import com.example.busmap.core.domain.models.BusStop
import kotlinx.coroutines.launch
import java.lang.Exception

class RouteViewModel(private val routeRepository: RouteRepository) : ViewModel() {
    //Info state
    private val routeInfoState = MutableLiveData<BusRouteInfo>()
    val routeInfo : LiveData<BusRouteInfo>
        get() = routeInfoState

    private val errorState = MutableLiveData<Boolean>()
    val error : LiveData<Boolean>
        get() = errorState

    private val loadingState = MutableLiveData<Boolean>()
    val loading : LiveData<Boolean>
        get() = loadingState

    //Stops state
    private val stopState = MutableLiveData<List<BusStop>>()
    val stops : LiveData<List<BusStop>>
        get() = stopState

    private val stopErrorState = MutableLiveData<Boolean>()
    val stopError : LiveData<Boolean>
        get() = errorState

    //Polyline states
    private val pathState = MutableLiveData<BusRoutePolyline>()
    val path : LiveData<BusRoutePolyline>
        get() = pathState

    private val pathErrorState = MutableLiveData<Boolean>()
    val pathError : LiveData<Boolean>
        get() = pathErrorState

    //Method to get states
    fun getInfo(busRoute: BusRoute) {
        loadingState.value = true
        viewModelScope.launch {
            try {
                //Get bus info and update value
                val busRouteInfo = routeRepository.getInfo(busRoute)
                routeInfoState.value = busRouteInfo
            } catch (ex : Exception) {
                //Error handling [TODO]
                errorState.value = true
            }
            loadingState.value = false
        }
    }

    fun getStops(busRoute: BusRoute) {
        viewModelScope.launch {
            try {
                val stops = routeRepository.getStopsOnRoute(busRoute)
                stopState.value = stops
            } catch (ex : Exception) {
                Log.e("ERROR", ex.message)
                stopErrorState.value = true
            }
        }
    }

    fun getPolyline(busRoute: BusRoute) {
        viewModelScope.launch {
            try {
                val polyline = routeRepository.getRoutePolyline(busRoute)
                pathState.value = polyline
            } catch (exception : Exception) {
                pathErrorState.value = true
            }
        }
    }
}