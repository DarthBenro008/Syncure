package com.benrostudios.syncure.data.network.response

sealed class NetworkResult<out T: Any>{
    data class Success<out T : Any>(val output : T) : NetworkResult<T>()
    data class Error(val exception: String)  : NetworkResult<Nothing>()
}