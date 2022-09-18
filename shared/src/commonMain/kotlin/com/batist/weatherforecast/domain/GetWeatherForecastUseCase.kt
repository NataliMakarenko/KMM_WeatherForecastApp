package com.batist.weatherforecast.domain

import com.batist.weatherforecast.data.network.WeatherApi
import com.batist.weatherforecast.data.network.WeatherForecastEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext


interface GetWeatherForecastUseCaseContract {
    suspend fun getWeatherForecast(city: String): WeatherForecastEntity?
}

class GetWeatherForecastUseCase(
    private val weatherApi: WeatherApi,
    private val dispatcher: CoroutineDispatcher
) :
    GetWeatherForecastUseCaseContract {
    override suspend fun getWeatherForecast(city: String): WeatherForecastEntity? {
        return withContext(dispatcher) {
            val result = weatherApi.getForecast(city)
            result
       }
    }
}