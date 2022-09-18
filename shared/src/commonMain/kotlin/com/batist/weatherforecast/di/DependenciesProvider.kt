package com.batist.weatherforecast.di

import com.batist.weatherforecast.presentation.viewmodel.MainViewModelContract


expect object DependenciesProvider {

    fun provideMainViewModel(): MainViewModelContract
}