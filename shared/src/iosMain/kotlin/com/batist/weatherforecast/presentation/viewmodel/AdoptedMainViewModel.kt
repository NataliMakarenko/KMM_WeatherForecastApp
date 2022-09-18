package com.batist.weatherforecast.presentation.viewmodel


class AdoptedMainViewModel(override val viewModel: MainViewModelContract) : CallbackViewModel() {

    val uiState = viewModel.uiState.asCallbacks()

    fun refreshForecast() {
        viewModel.refreshForecast()
    }
}
