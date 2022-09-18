package com.batist.weatherforecast.di

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.location.Geocoder
import com.batist.weatherforecast.data.CacheDataSource
import com.batist.weatherforecast.data.GetWeatherRepository
import com.batist.weatherforecast.data.LocationMapper
import com.batist.weatherforecast.data.LocationService
import com.batist.weatherforecast.domain.GetWeatherUseCase
import com.batist.weatherforecast.presentation.viewmodel.BaseMainViewModel
import com.batist.weatherforecast.presentation.viewmodel.MainViewModel
import com.batist.weatherforecast.presentation.viewmodel.MainViewModelContract
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.MainScope
import java.lang.IllegalStateException
import java.util.*

@SuppressLint("StaticFieldLeak")
actual object DependenciesProvider {

    @SuppressLint("StaticFieldLeak")
    private var context: Context? = null

    fun injectContext(context: Context) {
        this.context = context.applicationContext
    }

    actual fun provideMainViewModel(): MainViewModelContract {
       return context?.let {
            MainViewModel(
                BaseMainViewModel(
                    MainScope(),
                    GetWeatherUseCase(
                        GetWeatherRepository(
                            CacheDataSource(provideSharedPrefs(it)),
                            LocationService(
                                LocationServices.getFusedLocationProviderClient(it),
                                LocationMapper(),
                                Geocoder(context, Locale.ENGLISH)
                            ),
                            SharedDependenciesProvider.provideWeatherApi(),
                            SharedDependenciesProvider.provideDefaultDispatcher()
                        )
                    )
                )
            )
        }?: throw IllegalStateException("Context should be injected")
    }

    private fun provideSharedPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(
            "com.batist.weatherforecast.CACHE",
            Context.MODE_PRIVATE
        )
    }
}