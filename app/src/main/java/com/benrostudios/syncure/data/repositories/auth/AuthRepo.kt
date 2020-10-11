package com.benrostudios.syncure.data.repositories.auth

interface AuthRepo {
    fun register()
    fun signUp()
    fun verifyRegisterOtp()
    fun verifySignInOtp()
    fun resendOtp()
    fun forgotPassword()
}