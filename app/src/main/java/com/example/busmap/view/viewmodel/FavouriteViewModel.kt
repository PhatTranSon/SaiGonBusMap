package com.example.busmap.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.busmap.core.data.repository.BusRepository
import com.example.busmap.core.domain.models.BusStop
import com.example.busmap.view.wrappers.ErrorWrapper
import kotlinx.coroutines.launch
import java.lang.Exception

class FavouriteViewModel(private val busRepository: BusRepository) : ViewModel() {
    //States
    private val stopState = MutableLiveData<List<BusStop>>()
    val stops : LiveData<List<BusStop>>
        get() = stopState

    private val loadingState = MutableLiveData<Boolean>()
    val loading : LiveData<Boolean>
        get() = loadingState

    private val errorState = MutableLiveData<ErrorWrapper>()
    val error : LiveData<ErrorWrapper>
        get() = errorState

    //Methods
    fun getFavourites() {
        loadingState.value = true
        viewModelScope.launch {
            try {
                val stops = busRepository.getSavedBusStops()
                stopState.value = stops
                errorState.value = ErrorWrapper("", false)
            } catch (exception : Exception) {
                errorState.value = ErrorWrapper("Something went wrong", true)
            } finally {
                loadingState.value = false
            }
        }
    }

    fun removeFavourite(busStop : BusStop) {
        loadingState.value = true
        viewModelScope.launch {
            try {
                busRepository.delete(busStop)
                getFavourites()
            } catch (exception : Exception) {
                errorState.value = ErrorWrapper("Something went wrong", true)
            } finally {
                loadingState.value = false
            }
        }
    }
}