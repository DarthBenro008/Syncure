package com.benrostudios.syncure.data.models

import java.io.Serializable

data class Password(
    val _id: String,
    val title: String,
    val code: String
) : Serializable