package com.example.busmap.core.domain.models

data class BusRouteInfo(
    val id : Int,
    val number : String,
    val type : String,
    val distance : Double,
    val company : String,
    val timeOfTrip : String,
    val timeOfWait : String,
    val operationTime : String,
    val numberOfSeat : String,
    val outBound : String,
    val inBound : String,
    val name : String
) {
    override fun toString(): String {
        return "BusRouteInfo(id=$id, number='$number', type='$type', distance=$distance, company='$company', timeOfTrip='$timeOfTrip', timeOfWait='$timeOfWait', operationTime='$operationTime', numberOfSeat='$numberOfSeat', outBound='$outBound', inBound='$inBound', name='$name')"
    }
}