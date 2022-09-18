package com.batist.weatherforecast.domain

import com.batist.weatherforecast.data.Location
import com.batist.weatherforecast.data.LocationService

interface GetLocationUseCaseContract {
    suspend fun getLocation(): Result<Location>
}

class GetLocationUseCase(private val service: LocationService) :
    GetLocationUseCaseContract {
    override suspend fun getLocation(): Result<Location> = service.getCurrentLocation()
}