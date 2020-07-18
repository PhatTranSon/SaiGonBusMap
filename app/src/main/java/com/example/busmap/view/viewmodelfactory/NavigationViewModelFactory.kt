package com.example.busmap.view.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.busmap.view.viewmodel.NavigationViewModel
import com.example.busmap.core.data.repository.PathRepository
import com.example.busmap.core.framework.db.api.BusAPI
import com.example.busmap.core.framework.db.api.TrafficAPI
import com.example.busmap.core.framework.db.implementation.BusPathDataSourceImpl
import com.example.busmap.core.framework.db.mappers.rest.BusPathMapper
import java.lang.IllegalArgumentException

class NavigationViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NavigationViewModel::class.java)) {
            //Create repository
            val repository = PathRepository(
                BusPathDataSourceImpl(
                    BusAPI(),
                    TrafficAPI(),
                    BusPathMapper()
                )
            )

            //Return view model
            return NavigationViewModel(repository) as T
        }
        throw IllegalArgumentException("Invalid ViewModel class")
    }
}