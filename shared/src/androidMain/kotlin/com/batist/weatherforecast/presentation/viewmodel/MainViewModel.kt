package com.batist.weatherforecast.presentation.viewmodel

import androidx.lifecycle.ViewModel

internal actual class MainViewModel(baseMainViewModel: BaseMainViewModel) :ViewModel(),
    MainViewModelContract by baseMainViewModel