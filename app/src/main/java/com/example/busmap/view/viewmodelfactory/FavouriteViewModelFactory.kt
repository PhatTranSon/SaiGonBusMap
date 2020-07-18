package com.example.busmap.view.viewmodelfactory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.busmap.core.data.repository.BusRepository
import com.example.busmap.core.framework.db.api.BusAPI
import com.example.busmap.core.framework.db.database.BusStopDatabase
import com.example.busmap.core.framework.db.implementation.PersonalBusStopDataSourceImpl
import com.example.busmap.core.framework.db.implementation.PublicBusStopDataSourceImpl
import com.example.busmap.core.framework.db.mappers.rest.BusStopMapper
import com.example.busmap.view.viewmodel.FavouriteViewModel

class FavouriteViewModelFactory(private val context : Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavouriteViewModel::class.java)) {
            //Create room instance
            val busStopDatabase = BusStopDatabase(context)

            //Create api and mapper
            val api = BusAPI()
            val mapper = BusStopMapper()

            //Create repository
            val busRepository = BusRepository(
                PublicBusStopDataSourceImpl(
                    api,
                    mapper
                ),
                PersonalBusStopDataSourceImpl(
                    busStopDatabase.getDao(),
                    mapper
                )
            )

            return FavouriteViewModel(busRepository) as T
        }
        throw IllegalArgumentException("Invalid ViewModel Class")
    }
}