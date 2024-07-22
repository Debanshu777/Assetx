package com.debanshudatta.fintrack.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.debanshudatta.fintrack.data.domain.Result
import com.debanshudatta.fintrack.data.domain.error.DataError
import com.debanshudatta.fintrack.data.domain.model.Data
import com.debanshudatta.fintrack.data.domain.model.Stock
import com.debanshudatta.fintrack.data.domain.model.Type
import com.debanshudatta.fintrack.data.domain.model.Universe
import com.debanshudatta.fintrack.data.domain.polling.PollingCallback
import com.debanshudatta.fintrack.data.domain.polling.PollingManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.debanshudatta.fintrack.data.domain.usecases.HomeScreenDataUseCase
import kotlinx.coroutines.launch

class AppViewModel(
    private val homeScreenDataUseCase: HomeScreenDataUseCase
) : ViewModel(), PollingCallback {
    private val _universeDataList = MutableStateFlow<DataState>(DataState.Uninitialized)
    val universeDataList = _universeDataList.asStateFlow()
    private val pollingManager:PollingManager= PollingManager(this)

    init {
        pollingManager.startPolling(viewModelScope)
    }


    private fun getUniverseDataList(universe: Universe, type: Type) {
        _universeDataList.tryEmit(DataState.Loading)
        viewModelScope.launch {
            when (val response = homeScreenDataUseCase.getStockUniverse(universe, type)) {
                is Result.Error -> _universeDataList.tryEmit(
                    when (response.error) {
                        DataError.Local.DISK_FULL -> DataState.Error("DISK_FULL")
                        DataError.Network.REQUEST_TIMEOUT -> DataState.Error("REQUEST_TIMEOUT")
                        DataError.Network.UNAUTHORIZED -> DataState.Error("UNAUTHORIZED")
                        DataError.Network.CONFLICT -> DataState.Error("CONFLICT")
                        DataError.Network.SERIALIZATION -> DataState.Error("SERIALIZATION")
                        DataError.Network.NO_INTERNET -> DataState.Error("NO_INTERNET")
                        DataError.Network.PAYLOAD_TOO_LARGE -> DataState.Error("PAYLOAD_TOO_LARGE")
                        DataError.Network.SERVER_ERROR -> DataState.Error("SERVER_ERROR")
                        DataError.Network.UNKNOWN -> DataState.Error("UNKNOWN")
                    }
                )

                is Result.Success -> _universeDataList.tryEmit(DataState.Success(response.data))
            }
        }
    }

    override suspend fun onPoll() {
        getUniverseDataList(Universe.LargeCap, Type.gainers)
    }

    override fun onError(exception: Exception) {
        print(exception.message)
    }

    fun stopPolling(){
        pollingManager.stopPolling()
    }
}

sealed interface DataState {
    data class Success(val data: Data) : DataState
    data class Error(val error: String) : DataState
    data object Loading : DataState
    data object Uninitialized : DataState
}