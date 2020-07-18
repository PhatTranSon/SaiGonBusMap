package com.example.busmap.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.busmap.core.data.repository.PathRepository
import com.example.busmap.core.domain.models.BusNavigation
import com.example.busmap.core.domain.models.BusNavigationPolyline
import kotlinx.coroutines.launch

class DirectionViewModel(private val pathRepository: PathRepository) : ViewModel() {
    //States
    private val directionState = MutableLiveData<BusNavigationPolyline>()
    val direction : LiveData<BusNavigationPolyline>
    get() = directionState

    private val loadingState = MutableLiveData<Boolean>()
    val loading : LiveData<Boolean>
    get() = loadingState

    private val errorState = MutableLiveData<Boolean>()
    val error : LiveData<Boolean>
    get() = errorState

    //Method to get polyline
    fun getPolyline(busNavigation: BusNavigation) {
        loadingState.value = true
        viewModelScope.launch {
            //Get polyline
            try {
                val polyline = pathRepository.getDirection(busNavigation)
                directionState.value = polyline
            } catch (ex : Exception) {
                errorState.value = true
            }
        }
    }
}