package com.debanshudatta.fintrack

import com.debanshudatta.fintrack.error.DataError
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.parameters
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.SerializationException

class ClientWrapper() {
    suspend inline fun <reified T> networkGetUsecase(
        endpoint: String,
        queries: Map<String, String>?,
    ): Result<T, DataError.Network> {
        val response = try {
            networkClient.get(endpoint) {
                parameters {
                    if (queries != null) {
                        for ((key, value) in queries) {
                            parameter(key, value)
                        }
                    }
                }
            }
        } catch (ex: UnresolvedAddressException) {
            return Result.Error(DataError.Network.NO_INTERNET)
        } catch (ex: SerializationException) {
            return Result.Error(DataError.Network.SERIALIZATION)
        }
        return when (response.status.value) {
            in 200..299 -> {
                Result.Success(response.body<T>())
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