package com.example.busmap.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.busmap.core.data.repository.BusRepository
import com.example.busmap.core.domain.models.BusStop
import com.example.busmap.core.domain.models.Coordinate
import com.google.android.gms.maps.model.*
import kotlinx.coroutines.launch

class MapViewModel(
    private val busRepository: BusRepository
) : ViewModel() {
    //Exposed states
    private val busStopState = MutableLiveData<List<BusStop>>()
    val busStops : LiveData<List<BusStop>>
    get() = busStopState as LiveData<List<BusStop>>

    fun getBusStops(bounds : LatLngBounds) {
        viewModelScope.launch {
            //Get the stops
            val busStops = busRepository.getPublicBusStops(
                Coordinate(lat = bounds.southwest.latitude, lng = bounds.southwest.longitude),
                Coordinate(lat = bounds.northeast.latitude, lng = bounds.northeast.longitude)
            )

            //Set fields
            busStopState.value = busStops
        }
    }
}