package com.batist.weatherforecast.data

import com.batist.weatherforecast.data.network.WeatherApi
import com.batist.weatherforecast.domain.GetWeatherRepositoryContract
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.withContext

class GetWeatherRepository(
    private val cache: CacheDataSource,
    private val service: LocationService,
    private val weatherApi: WeatherApi,
    private val dispatcher: CoroutineDispatcher
) : GetWeatherRepositoryContract {
    override fun getWeather(): Flow<Result<WeatherInfo>> {
        return channelFlow {
            withContext(dispatcher) {
                val cacheData = cache.getCache()
                cacheData?.let { it ->
                    send(Result.success(it))
                }
                val loc = service.getCurrentLocation()
                if (loc.isSuccess) {
                    loc.getOrNull()?.let {
                        val forecastEntity = weatherApi.getForecast(it.city)
                        if (forecastEntity != null) {
                            val cacheInfo = WeatherInfo(it, forecastEntity)
                            cache.saveCache(cacheInfo)
                            send(Result.success(cacheInfo))
                        } else {
                            send(Result.failure(Throwable("Server error")))
                        }
                    } ?: send(
                        Result.failure(
                            loc.exceptionOrNull()
                                ?: Throwable("Error while retrieving location info")
                        )
                    )
                } else {
                    send(
                        Result.failure(
                            loc.exceptionOrNull()
                                ?: Throwable("Error while retrieving location info")
                        )
                    )
                }
            }
        }
    }
}