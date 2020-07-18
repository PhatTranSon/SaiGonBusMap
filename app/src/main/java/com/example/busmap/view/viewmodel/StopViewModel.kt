package com.example.busmap.view.viewmodel

import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.busmap.core.data.repository.BusRepository
import com.example.busmap.core.data.repository.RouteRepository
import com.example.busmap.core.domain.models.BusRoute
import com.example.busmap.core.domain.models.BusStop
import com.example.busmap.view.wrappers.ErrorWrapper
import kotlinx.coroutines.launch
import java.lang.Exception

class StopViewModel(
    private val routeRepository: RouteRepository,
    private val stopRepository: BusRepository
) : ViewModel() {
    //List of routes
    private val routeState = MutableLiveData<List<BusRoute>>()
    val routes : LiveData<List<BusRoute>>
        get() = routeState

    //Loading and error state for api
    private val loadingApiState = MutableLiveData<Boolean>()
    val loadingApi : LiveData<Boolean>
        get() = loadingApiState

    private val errorApiState = MutableLiveData<Boolean>()
    val errorApi : LiveData<Boolean>
        get() = errorApiState

    //Loading and error state for room
    private val loadingRoomState = MutableLiveData<Boolean>()
    val loadingRoom : LiveData<Boolean>
        get() = loadingRoomState

    private val errorRoomState = MutableLiveData<ErrorWrapper>()
    val errorRoom : LiveData<ErrorWrapper>
        get() = errorRoomState

    //Method to retrieve bus routes
    fun getBusRoutes(busStop : BusStop) {
        //Set loading true
        loadingApiState.value = true

        //Try to get the resource
        viewModelScope.launch {
            try {
                routeRepository.getRoutes(busStop).also {
                    routeState.value = it
                }
            } catch (exception : Exception) {
                errorApiState.value = true
            }

            //Not loading
            loadingApiState.value = false
        }
    }

    //CRUD bus stops
    fun addRoute(busStop: BusStop) {
        loadingRoomState.value = true
        viewModelScope.launch {
            try {
                //Add the bus stop
                stopRepository.add(busStop)
                errorRoomState.value = ErrorWrapper("", false)
            } catch (constraintException : SQLiteConstraintException) {
                errorRoomState.value = ErrorWrapper("Already added", true)
            }  catch (exception : Exception) {
                errorRoomState.value = ErrorWrapper("Something went wrong", true)
            } finally {
                loadingRoomState.value = false
            }
        }
    }
}