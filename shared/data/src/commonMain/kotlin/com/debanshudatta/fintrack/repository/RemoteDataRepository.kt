package com.debanshudatta.fintrack.data.repository

import com.debanshudatta.fintrack.ClientWrapper
import com.debanshudatta.fintrack.Result
import com.debanshudatta.fintrack.data.model.IndicesResponse
import com.debanshudatta.fintrack.data.model.Response
import com.debanshudatta.fintrack.data.utils.Constants
import com.debanshudatta.fintrack.error.DataError

class RemoteDataRepository {
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
