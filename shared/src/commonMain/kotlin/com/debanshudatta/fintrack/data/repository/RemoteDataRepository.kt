package com.debanshudatta.fintrack.data.repository

import com.debanshudatta.fintrack.data.domain.Result
import com.debanshudatta.fintrack.data.domain.error.DataError
import com.debanshudatta.fintrack.data.domain.model.Data
import com.debanshudatta.fintrack.data.domain.model.Response
import com.debanshudatta.fintrack.data.networkClient
import com.debanshudatta.fintrack.utils.Constants
import io.ktor.client.request.get
import io.ktor.client.call.body
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.SerializationException

internal class RemoteDataRepository {
    suspend fun getStockUniverse(universe: String, type: String): Result<Data, DataError> {
        val response = try {
            networkClient.get(
                Constants.AnalyzeBaseUrl + Constants.HomePageStockPath
            ) {
                url {
                    parameters.append("universe", universe)
                    parameters.append("type", type)
                }
            }
        } catch (ex: UnresolvedAddressException) {
            return Result.Error(DataError.Network.NO_INTERNET)
        }catch (ex: SerializationException) {
            return Result.Error(DataError.Network.SERIALIZATION)
        }
        return when(response.status.value) {
            in 200..299 -> {
                val apiResult = response.body<Response>()
                Result.Success(apiResult.data)
            }
            401 -> Result.Error(DataError.Network.UNAUTHORIZED)
            409 -> Result.Error(DataError.Network.CONFLICT)
            408 -> Result.Error(DataError.Network.REQUEST_TIMEOUT)
            413 -> Result.Error(DataError.Network.PAYLOAD_TOO_LARGE)
            in 500..599 -> Result.Error(DataError.Network.SERVER_ERROR)
            else -> Result.Error(DataError.Network.UNKNOWN)
        }
    }
}