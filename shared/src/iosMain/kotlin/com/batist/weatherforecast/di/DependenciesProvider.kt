package com.batist.weatherforecast.di

import com.batist.weatherforecast.data.CacheDataSource
import com.batist.weatherforecast.data.GetWeatherRepository
import com.batist.weatherforecast.data.LocationMapper
import com.batist.weatherforecast.data.LocationService
import com.batist.weatherforecast.domain.GetLocationUseCase
import com.batist.weatherforecast.domain.GetWeatherForecastUseCase
import com.batist.weatherforecast.domain.GetWeatherUseCase
import com.batist.weatherforecast.presentation.viewmodel.BaseMainViewModel
import com.batist.weatherforecast.presentation.viewmodel.MainViewModel
import com.batist.weatherforecast.presentation.viewmodel.MainViewModelContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import platform.CoreLocation.CLLocationManager

actual object DependenciesProvider {
    actual fun provideMainViewModel(): MainViewModelContract {
        return MainViewModel(
            BaseMainViewModel(
                MainScope(),
                GetWeatherUseCase(
                    GetWeatherRepository(
                        CacheDataSource(),
                        LocationService(CLLocationManager(), LocationMapper()),
                        SharedDependenciesProvider.provideWeatherApi(),
                        Dispatchers.Main
                    )
                )
                //GetLocationUseCase(LocationService(CLLocationManager(), LocationMapper())),
                //GetWeatherForecastUseCase(
                //SharedDependenciesProvider.provideWeatherApi(),
                // SharedDependenciesProvider.provideDefaultDispatcher()
                //)
            )
        )
    }
}