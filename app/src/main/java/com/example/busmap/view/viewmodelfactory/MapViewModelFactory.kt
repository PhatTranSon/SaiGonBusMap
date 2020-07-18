package com.example.busmap.view.viewmodelfactory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.busmap.view.viewmodel.MapViewModel
import com.example.busmap.core.data.repository.BusRepository
import com.example.busmap.core.framework.db.api.BusAPI
import com.example.busmap.core.framework.db.database.BusStopDatabase
import com.example.busmap.core.framework.db.implementation.PersonalBusStopDataSourceImpl
import com.example.busmap.core.framework.db.implementation.PublicBusStopDataSourceImpl
import com.example.busmap.core.framework.db.mappers.rest.BusStopMapper
import java.lang.IllegalArgumentException

class MapViewModelFactory(private val context : Context)
    : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MapViewModel::class.java)) {
            //Create room database
            val busRoomDatabase = BusStopDatabase(context)

            //Create mapper
            val mapper = BusStopMapper()
            //Create the repository [Can be fixed by dependency injection]
            val busRepository = BusRepository(
                PublicBusStopDataSourceImpl(
                    BusAPI(),
                    mapper
                ),
                PersonalBusStopDataSourceImpl(
                    busRoomDatabase.getDao(),
                    mapper
                )
            )
            return MapViewModel(busRepository) as T
        }
        throw IllegalArgumentException("Invalid ViewModel class")
    }

}