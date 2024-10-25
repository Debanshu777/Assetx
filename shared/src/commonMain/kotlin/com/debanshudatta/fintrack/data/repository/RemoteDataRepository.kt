package com.debanshudatta.fintrack.data.repository

import com.debanshudatta.fintrack.ClientWrapper
import com.debanshudatta.fintrack.Result
import com.debanshudatta.fintrack.data.domain.model.IndicesResponse
import com.debanshudatta.fintrack.data.domain.model.Response
import com.debanshudatta.fintrack.error.DataError
import com.debanshudatta.fintrack.utils.Constants

internal class RemoteDataRepository {
    private val clientWrapper = ClientWrapper()
    suspend fun getStockUniverse(universe: String, type: String): Result<Response, DataError> {
        return clientWrapper.networkGetUsecase<Response>(
            Constants.ANALYZE_BASE_URL + Constants.HOME_PAGE_STOCK_PATH,
            mapOf("universe" to universe, "type" to type)
        )
    }

    suspend fun getIndexHomePage(): Result<IndicesResponse, DataError> {
        return clientWrapper.networkGetUsecase<IndicesResponse>(
            Constants.ANALYZE_BASE_URL + Constants.HOME_PAGE_INFICES_PATH,
           null
        )
    }
}
