package com.example.busmap.core.framework.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.busmap.core.framework.db.entities.room.BusStopRoomEntity

@Dao
interface BusStopDAO {
    @Query("SELECT * FROM stops")
    suspend fun getAll() : List<BusStopRoomEntity>

    @Insert
    suspend fun add(busStopRoomEntity: BusStopRoomEntity) : Long

    @Query("DELETE FROM stops WHERE stopId = :stopId")
    suspend fun delete(stopId : String)
}