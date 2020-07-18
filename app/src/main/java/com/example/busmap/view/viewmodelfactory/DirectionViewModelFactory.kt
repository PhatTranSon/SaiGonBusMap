package com.example.busmap.view.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.busmap.core.data.repository.PathRepository
import com.example.busmap.core.framework.db.api.BusAPI
import com.example.busmap.core.framework.db.api.TrafficAPI
import com.example.busmap.core.framework.db.implementation.BusPathDataSourceImpl
import com.example.busmap.core.framework.db.mappers.rest.BusPathMapper
import com.example.busmap.view.viewmodel.DirectionViewModel
import java.lang.IllegalArgumentException

class DirectionViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DirectionViewModel::class.java)) {
            //Create instance
            val pathRepository = PathRepository(
                BusPathDataSourceImpl(
                    BusAPI(),
                    TrafficAPI(),
                    BusPathMapper()
                )
            )

            //Return
            return DirectionViewModel(pathRepository) as T
        }
        throw IllegalArgumentException("Invalid ViewModel class")
    }
}