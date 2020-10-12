package com.benrostudios.syncure.data.repositories.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.benrostudios.syncure.data.network.AuthService
import com.benrostudios.syncure.data.network.response.GenericResponse
import com.benrostudios.syncure.data.repositories.BaseRepository
import com.benrostudios.syncure.utils.SharedPrefManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRepoImpl(
    private val sharedPrefManager: SharedPrefManager,
    private val authService: AuthService
) : AuthRepo, BaseRepository() {

    val _authResponse = MutableLiveData<GenericResponse>()
    val _otpResponse = MutableLiveData<GenericResponse>()

    override suspend fun register(
        username: String,
        email: String,
        name: String?
    ): LiveData<GenericResponse> {
        return withContext(Dispatchers.IO) {
            _authResponse.postValue(
                safeApiCall(
                    call = { authService.userRegister(username, email, name) },
                    error = "Unable to register"
                )
            )
            return@withContext _authResponse
        }
    }

    override suspend fun signIn(username: String, password: String): LiveData<GenericResponse> {
        return withContext(Dispatchers.IO) {
            _authResponse.postValue(
                safeApiCall(
                    call = { authService.userLogin(username, password) },
                    error = "Unable to register"
                )
            )
            return@withContext _authResponse
        }
    }

    override suspend fun verifyRegisterOtp(
        totp: String,
        password: String,
        username: String
    ): LiveData<GenericResponse> {
        return withContext(Dispatchers.IO) {
            _otpResponse.postValue(
                safeApiCall(
                    call = { authService.twoFactorRegister(username, totp, password) },
                    error = "Unable to verify OTP"
                )
            )
            return@withContext _otpResponse
        }
    }

    override suspend fun verifySignInOtp(
        totp: String,
        username: String
    ): LiveData<GenericResponse> {
        return withContext(Dispatchers.IO) {
            _otpResponse.postValue(
                safeApiCall(
                    call = { authService.twoFactorLogin(username, totp) },
                    error = "Unable to verify OTP"
                )
            )
            return@withContext _otpResponse
        }
    }

    override suspend fun resendOtp(totp: String) {
        TODO("Not yet implemented")
    }

    override suspend fun forgotPassword(username: String): LiveData<Boolean> {
        TODO("Not yet implemented")
    }

    override val networkError: LiveData<String>
        get() = _networkErrorResolution
}