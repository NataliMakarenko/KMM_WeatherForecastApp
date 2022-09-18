package com.batist.weatherforecast.data

import kotlinx.serialization.Serializable


interface LocationServiceContract {
    suspend fun getCurrentLocation(): Result<Location>
}

@Serializable
data class Location(val lat: Double, val lng: Double, val country: String, val city: String)

expect class LocationService {
    suspend fun getCurrentLocation(): Result<Location>
}