package com.batist.weatherforecast.presentation.viewmodel

import kotlinx.coroutines.flow.Flow

internal actual class MainViewModel(baseMainViewModel: BaseMainViewModel) :
    MainViewModelContract by baseMainViewModel

abstract class CallbackViewModel {
    internal abstract val viewModel: MainViewModelContract

    /**
     * Create a [FlowAdapter] from this [Flow] to make it easier to interact with from Swift.
     */
    fun <T : Any> Flow<T>.asCallbacks() =
        FlowAdapter(viewModel.viewModelScope, this)

    @Suppress("Unused") // Called from Swift
    fun clear() = viewModel.clear()
}