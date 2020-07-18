package com.example.busmap.core.domain.models

data class BusRoute(
    val from : String,
    val fromId : String,
    val to : String,
    val toId : String,
    val id : String,
    val schedules : List<BusSchedule>,
    val name : String,
    val number : String
) {
    override fun toString(): String {
        return "BusRoute(from='$from', fromId='$fromId', to='$to', toId='$toId', id='$id', schedules=$schedules, name='$name')"
    }
}