package com.benrostudios.syncure.data.network.response

import com.benrostudios.syncure.data.models.Password

data class ResponseMetrics(
    val response: String? = null,
    val timeRemaining: Int? = null,
    val token: String? = null,
    val device: String? = null,
    val uuid: String? = null,
    val memoryUsed: String? = null,
    val remaining: String? = null,
    val unit: String? = null,
    val req_id: String? = null,
    val foundItems: List<Password>? = emptyList()
)