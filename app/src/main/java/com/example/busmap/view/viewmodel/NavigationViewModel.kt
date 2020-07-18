package com.example.busmap.view.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.busmap.core.data.repository.PathRepository
import com.example.busmap.core.domain.models.BusNavigation
import com.example.busmap.core.domain.models.Coordinate
import kotlinx.coroutines.launch
import java.lang.Exception

class NavigationViewModel(private val pathRepository: PathRepository) : ViewModel() {
    //States
    private val pathState = MutableLiveData<List<BusNavigation>>()
    val paths : LiveData<List<BusNavigation>>
    get() = pathState

    private val loadingState = MutableLiveData<Boolean>()
    val loading : LiveData<Boolean>
    get() = loadingState

    private val errorState = MutableLiveData<Boolean>()
    val error : LiveData<Boolean>
    get() = errorState

    fun getPath(startCoordinate: Coordinate, endCoordinate: Coordinate) {
        viewModelScope.launch {
            loadingState.value = true
            try {
                val results = pathRepository.getNavigation(startCoordinate, endCoordinate)
                pathState.value = results
                errorState.value = false
            } catch (exception : Exception) {
                errorState.value = true
            }
            loadingState.value = false
        }
    }

    fun getDirection(busNavigation: BusNavigation) {
        viewModelScope.launch {
            val direction = pathRepository.getDirection(busNavigation)
            Log.i("DIR", direction.toString())
        }
    }
}