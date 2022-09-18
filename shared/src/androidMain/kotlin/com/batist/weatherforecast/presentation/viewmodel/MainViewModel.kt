package com.batist.weatherforecast.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel

internal actual class MainViewModel(baseMainViewModel: BaseMainViewModel) :ViewModel(),
    MainViewModelContract by baseMainViewModel{
        init {
            Log.d("TEST", "refresh forecast")
            refreshForecast()
        }
    }