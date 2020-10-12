package com.benrostudios.syncure.data.repositories.auth

import androidx.lifecycle.LiveData
import com.benrostudios.syncure.data.repositories.BaseRepository

class AuthRepoImpl : AuthRepo, BaseRepository() {


    override suspend fun register(username: String, password: String): LiveData<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun signIn(username: String, password: String): LiveData<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun verifyRegisterOtp(totp: String, password: String, username: String) {
        TODO("Not yet implemented")
    }

    override suspend fun verifySignInOtp(totp: String, username: String) {
        TODO("Not yet implemented")
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