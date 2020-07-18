package com.example.busmap.core.framework.db.api

import com.example.busmap.core.framework.db.services.TrafficService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TrafficAPI {
    companion object {
        private const val BASE_URL = "https://maps.googleapis.com"
        private const val API_KEY = "YOUR_KEY_HERE"
    }

    private val trafficService : TrafficService

    init {
        //Create retrofit instance
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()

        //Create service
        trafficService = retrofit.create(TrafficService::class.java)
    }

    suspend fun getDirection(startLat : Double, startLng : Double, endLat : Double, endLng : Double)
        = trafficService.getDirection(
        startLat.toString() + "," + startLng.toString(),
        endLat.toString() + "," + endLng.toString(),
        API_KEY)
}
