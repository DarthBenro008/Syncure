package com.benrostudios.syncure.data.repositories.auth

import androidx.lifecycle.LiveData
import com.benrostudios.syncure.data.network.response.GenericResponse
import com.benrostudios.syncure.data.network.response.NetworkResult

interface AuthRepo {
    suspend fun register(username: String, email: String, name: String?): LiveData<GenericResponse>
    suspend fun signIn(username: String, password: String): LiveData<GenericResponse>
    suspend fun verifyRegisterOtp(totp: String, password: String, username: String): LiveData<GenericResponse>
    suspend fun verifySignInOtp(totp: String, username: String): LiveData<GenericResponse>
    suspend fun resendOtp(totp: String)
    suspend fun forgotPassword(username: String): LiveData<Boolean>
    val networkError: LiveData<String>
}