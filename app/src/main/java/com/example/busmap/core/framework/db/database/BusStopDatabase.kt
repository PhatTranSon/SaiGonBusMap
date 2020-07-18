package com.example.busmap.core.framework.db.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.busmap.core.framework.db.dao.BusStopDAO
import com.example.busmap.core.framework.db.entities.room.BusStopRoomEntity

@Database(
    entities = [BusStopRoomEntity::class],
    version = 3
)
abstract class BusStopDatabase : RoomDatabase() {
    abstract fun getDao() : BusStopDAO
    companion object {
        //Thread-safe singleton pattern
        @Volatile private var instance : BusStopDatabase? = null
        private val LOCK = Any()
        operator fun invoke(context : Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        //Build database
        private fun buildDatabase(context : Context) = Room.databaseBuilder(
            context,
            BusStopDatabase::class.java,
            "bus_stop_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}