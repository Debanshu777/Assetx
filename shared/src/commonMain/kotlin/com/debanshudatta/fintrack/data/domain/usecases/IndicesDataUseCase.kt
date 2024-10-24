package com.debanshudatta.fintrack.data.domain.usecases

import com.debanshudatta.fintrack.data.domain.Result
import com.debanshudatta.fintrack.data.domain.error.DataError
import com.debanshudatta.fintrack.data.domain.model.Indices
import com.debanshudatta.fintrack.data.repository.RemoteDataRepository
import com.debanshudatta.fintrack.utils.Dispatcher
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class IndicesDataUseCase : KoinComponent {
    private val remoteDataRepository: RemoteDataRepository by inject()
    private val dispatcher: Dispatcher by inject()

    suspend fun getIndexHomePage(): Result<List<Indices>, DataError> =
        withContext(dispatcher.io) {
            remoteDataRepository.getIndexHomePage()
        }
}
