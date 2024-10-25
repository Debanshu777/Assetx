package com.debanshudatta.fintrack.data.domain.usecases

import com.debanshudatta.fintrack.Result
import com.debanshudatta.fintrack.data.domain.model.Data
import com.debanshudatta.fintrack.data.domain.model.Type
import com.debanshudatta.fintrack.data.domain.model.Universe
import com.debanshudatta.fintrack.data.repository.RemoteDataRepository
import com.debanshudatta.fintrack.error.DataError
import com.debanshudatta.fintrack.utils.Dispatcher
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class HomeScreenDataUseCase : KoinComponent {
    private val remoteDataRepository: RemoteDataRepository by inject()
    private val dispatcher: Dispatcher by inject()

    suspend fun getStockUniverse(universe: Universe, type: Type): Result<Data, DataError> =
        withContext(dispatcher.io) {
            when(val responseData = remoteDataRepository.getStockUniverse(universe.name, type.value)){
                is Result.Success -> {
                    Result.Success(responseData.data.data)
                }
                is Result.Error -> {
                    Result.Error(responseData.error)
                }
            }
        }
}
