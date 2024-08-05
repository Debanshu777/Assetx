package com.debanshudatta.fintrack.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.debanshudatta.fintrack.data.domain.Result
import com.debanshudatta.fintrack.data.domain.error.DataError
import com.debanshudatta.fintrack.data.domain.model.Indices
import com.debanshudatta.fintrack.data.domain.model.Stock
import com.debanshudatta.fintrack.data.domain.model.Type
import com.debanshudatta.fintrack.data.domain.model.Universe
import com.debanshudatta.fintrack.data.domain.polling.PollingCallback
import com.debanshudatta.fintrack.data.domain.polling.PollingManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.debanshudatta.fintrack.data.domain.usecases.HomeScreenDataUseCase
import com.debanshudatta.fintrack.data.domain.usecases.IndicesDataUseCase
import kotlinx.coroutines.launch

class AppViewModel(
    private val homeScreenDataUseCase: HomeScreenDataUseCase,
    private val indicesDataUseCase: IndicesDataUseCase
) : ViewModel(), PollingCallback {
    private val _universeDataList = MutableStateFlow<DataState<Stock>>(DataState.Uninitialized)
    val universeDataList = _universeDataList.asStateFlow()

    private val _indicesList = MutableStateFlow<DataState<Indices>>(DataState.Uninitialized)
    val indicesList = _indicesList.asStateFlow()

    private val _universe = MutableStateFlow(Universe.LargeCap)
    private val _type = MutableStateFlow(Type.gainers)

    private val pollingManager: PollingManager = PollingManager(this)

    init {
        pollingManager.startPolling(viewModelScope)
    }

    fun setUniverse(universe: Universe) {
        _universe.value = universe
        getUniverseDataList()
    }

    fun setType(type: Type) {
        _type.value = type
        _universeDataList.tryEmit(DataState.Loading)
        getUniverseDataList()
    }

    private fun getIndicesList() {
        viewModelScope.launch {
            when(val response = indicesDataUseCase.getIndexHomePage()){
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
                is Result.Success -> _indicesList.tryEmit(DataState.Success(response.data))
            }
        }
    }

    private fun getUniverseDataList() {
        viewModelScope.launch {
            when (val response =
                homeScreenDataUseCase.getStockUniverse(_universe.value, _type.value)) {
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

                is Result.Success -> {
                    val incomingData = when (_type.value) {
                        Type.gainers -> {
                            response.data.gainers
                        }

                        Type.active -> {
                            response.data.active
                        }

                        Type.losers -> {
                            response.data.losers
                        }

                        Type.approachingHigh -> {
                            response.data.approachingHigh
                        }

                        Type.approachingLow -> {
                            response.data.approachingLow
                        }
                    }
                    _universeDataList.tryEmit(DataState.Success(incomingData))
                }
            }
        }
    }

    override suspend fun onPoll() {
        getUniverseDataList()
        getIndicesList()
    }

    override fun onError(exception: Exception) {
        print(exception.message)
    }

    fun stopPolling() {
        pollingManager.stopPolling()
    }
}

sealed interface DataState<out T> {
    data class Success<T>(val stocks: List<T>) : DataState<T>
    data class Error(val error: String) : DataState<Nothing>
    data object Loading : DataState<Nothing>
    data object Uninitialized : DataState<Nothing>
}