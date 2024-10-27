package com.debanshudatta.fintrack.usecase

import com.debanshudatta.fintrack.Result
import com.debanshudatta.fintrack.data.model.Indices
import com.debanshudatta.fintrack.data.repository.RemoteDataRepository
import com.debanshudatta.fintrack.error.DataError
import com.debanshudatta.fintrack.utils.Dispatcher
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class IndicesDataUseCase : KoinComponent {
    private val remoteDataRepository: RemoteDataRepository by inject()
    private val dispatcher: Dispatcher by inject()

    suspend fun getIndexHomePage(): Result<List<Indices>, DataError> =
        withContext(dispatcher.io) {
            when (val responseData = remoteDataRepository.getIndexHomePage()) {
                is Result.Success -> {
                    Result.Success(responseData.data.data)
                }

                is Result.Error -> {
                    Result.Error(responseData.error)
                }
            }
        }
}
