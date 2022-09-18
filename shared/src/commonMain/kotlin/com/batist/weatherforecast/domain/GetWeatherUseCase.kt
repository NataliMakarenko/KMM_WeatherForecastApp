package com.batist.weatherforecast.domain

import com.batist.weatherforecast.data.WeatherInfo
import kotlinx.coroutines.flow.Flow

class GetWeatherUseCase(private val repository: GetWeatherRepositoryContract) {
    fun getWeather(): Flow<Result<WeatherInfo>> {
        return repository.getWeather()
    }
}