package com.debanshudatta.fintrack

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.debanshudatta.fintrack.data.model.Indices
import com.debanshudatta.fintrack.data.model.Stock
import com.debanshudatta.fintrack.data.model.Type
import com.debanshudatta.fintrack.data.model.Universe
import com.debanshudatta.fintrack.entities.AssetEntity
import com.debanshudatta.fintrack.entities.AssetType
import com.debanshudatta.fintrack.error.DataError
import com.debanshudatta.fintrack.polling.PollingCallback
import com.debanshudatta.fintrack.polling.PollingManager
import com.debanshudatta.fintrack.usecase.GetStockAssetDataUseCase
import com.debanshudatta.fintrack.usecase.HomeScreenDataUseCase
import com.debanshudatta.fintrack.usecase.IndicesDataUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AppViewModel(
    private val homeScreenDataUseCase: HomeScreenDataUseCase,
    private val indicesDataUseCase: IndicesDataUseCase,
    private val getStockAssetDataUseCase: GetStockAssetDataUseCase
) : ViewModel(), PollingCallback {

    private val _universe = MutableStateFlow(Universe.LargeCap)
    private val _type = MutableStateFlow(Type.GAINERS)
    private val pollingManager: PollingManager = PollingManager(this)

    private val _universeDataList =
        MutableStateFlow<DataState<List<Stock>>>(DataState.Uninitialized)
    val universeDataList = _universeDataList.asStateFlow()
    private val _indicesList =
        MutableStateFlow<DataState<List<Indices>>>(DataState.Uninitialized)
    val indicesList = _indicesList.asStateFlow()
    private val _stockAssetData =
        MutableStateFlow<DataState<List<AssetEntity>>>(DataState.Uninitialized)
    val stockAssetData = _stockAssetData.asStateFlow()

    init {
        pollingManager.startPolling(viewModelScope)
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                getStockAssetDataUseCase.upsertStockAsset(
                    AssetEntity(
                        name = "Apple",
                        mappingId = "AAPL",
                        type = AssetType.STOCK,
                        marketValue = 100.0,
                        averagePurchasedValue = 10.0,
                        quantity = 1000,
                        transactions = emptyList()
                    )
                )
            }
            getStockAssetDataUseCase
                .getStockAssetData(AssetType.STOCK)
                .onStart { _stockAssetData.tryEmit(DataState.Loading) }
                .catch { error ->
                    _stockAssetData.tryEmit(DataState.Error(error.message ?: "Unknown Error"))
                }.collect { items ->
                    _stockAssetData.tryEmit(DataState.Success(items))
                }
        }
    }

    fun setUniverse(universe: Universe) {
        _universe.value = universe
        getUniverseDataList()
    }

    fun setType(type: Type) {
        if (_type.value == type) return
        _type.value = type
        _universeDataList.tryEmit(DataState.Loading)
        getUniverseDataList()
    }

    private fun getIndicesList() {
        viewModelScope.launch {
            when (val response = indicesDataUseCase.getIndexHomePage()) {
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
                        Type.GAINERS -> {
                            response.data.gainers
                        }

                        Type.ACTIVE -> {
                            response.data.active
                        }

                        Type.LOSERS -> {
                            response.data.losers
                        }

                        Type.APPROACHING_HIGH -> {
                            response.data.approachingHigh
                        }

                        Type.APPROACHING_LOW -> {
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

    override fun onCleared() {
        stopPolling()
        super.onCleared()
    }

    private fun stopPolling() {
        pollingManager.stopPolling()
    }
}

sealed interface DataState<out T> {
    data class Success<T>(val stocks: T) : DataState<T>
    data class Error(val error: String) : DataState<Nothing>
    data object Loading : DataState<Nothing>
    data object Uninitialized : DataState<Nothing>
}