package com.example.busmap.core.framework.db.entities.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "stops",
    indices = [Index(value = ["stopId"], unique = true)]
)
data class BusStopRoomEntity(
    @PrimaryKey(autoGenerate = true) val uid : Int,
    @ColumnInfo(name = "name") val name : String,
    @ColumnInfo(name = "code") val code : String,
    @ColumnInfo(name = "stopId") val restId : String,
    @ColumnInfo(name = "address") val address : String,
    @ColumnInfo(name = "lat") val lat : Double,
    @ColumnInfo(name = "lng") val lng : Double,
    @ColumnInfo(name = "search") val search : String,
    @ColumnInfo(name = "status") val status : String,
    @ColumnInfo(name = "type") val type : String,
    @ColumnInfo(name = "street") val street : String,
    @ColumnInfo(name = "ward") val ward : String,
    @ColumnInfo(name = "zone") val zone : String
)