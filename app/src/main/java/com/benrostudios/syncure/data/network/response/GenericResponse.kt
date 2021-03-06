package com.benrostudios.syncure.data.network.response

data class GenericResponse(
    val status: String,
    val message: String,
    val errors: List<String>?,
    val data: ResponseMetrics
)