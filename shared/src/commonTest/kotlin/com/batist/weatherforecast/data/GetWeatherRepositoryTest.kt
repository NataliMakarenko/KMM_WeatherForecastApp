package com.batist.weatherforecast.data

import com.batist.weatherforecast.data.network.WeatherApi
import com.batist.weatherforecast.data.network.WeatherDescription
import com.batist.weatherforecast.data.network.WeatherForecastEntity
import com.batist.weatherforecast.mocks.Mocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.test.runTest
import kotlin.test.*

//To run tests uncomment in shared build.gradle.kts//implementation("io.mockk:mockk:1.12.8")
class GetWeatherRepositoryTest {

    private val mockCacheDataSource = mockk<CacheDataSource>(relaxed = true)
    private val mockLocationService = mockk<LocationService>()
    private val mockWeatherApi = mockk<WeatherApi>()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Verify that cached data emitted`() = runTest {
        val repository = GetWeatherRepository(
            mockCacheDataSource,
            mockLocationService,
            mockWeatherApi,
            Dispatchers.Unconfined
        )
        val fakeCacheData = WeatherInfo(
            Mocks.location,
            Mocks.weatherForecastForCache
        )
        every { mockCacheDataSource.getCache() } returns fakeCacheData

        coEvery { mockLocationService.getCurrentLocation() } returns
                Result.success(Mocks.location)
        coEvery { mockWeatherApi.getForecast("Some city") } returns
                Mocks.weatherForecast
        val result = repository.getWeather().first()
        assertTrue(result.isSuccess)
        assertEquals(fakeCacheData, result.getOrThrow())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Verify that weather entity data emitted`() = runTest {
        val repository = GetWeatherRepository(
            mockCacheDataSource,
            mockLocationService,
            mockWeatherApi,
            Dispatchers.Unconfined
        )
        val fakeCacheData = WeatherInfo(
            Mocks.location,
            Mocks.weatherForecastForCache
        )
        every { mockCacheDataSource.getCache() } returns fakeCacheData

        coEvery { mockLocationService.getCurrentLocation() } returns
                Result.success(Mocks.location)
        coEvery { mockWeatherApi.getForecast("Some city") } returns
                Mocks.weatherForecast
        val firstResult = repository.getWeather().first()
        assertTrue(firstResult.isSuccess)
        assertEquals(fakeCacheData, firstResult.getOrThrow())
        val secondResult = repository.getWeather().drop(1).first()
        assertTrue(secondResult.isSuccess)
        assertEquals(Mocks.weatherForecast, secondResult.getOrThrow().weatherForecastEntity)
    }
}