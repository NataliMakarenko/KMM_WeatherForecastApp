package com.batist.weatherforecast.data

import com.batist.weatherforecast.data.network.WeatherForecastEntity
import kotlinx.serialization.Serializable

internal const val CACHE = "cache"

expect class CacheDataSource {
    fun getCache(): WeatherInfo?
    fun saveCache(cacheData: WeatherInfo)
}

@Serializable
data class WeatherInfo(val location: Location, val weatherForecastEntity: WeatherForecastEntity)

