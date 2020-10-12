package com.benrostudios.syncure.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.benrostudios.syncure.data.network.response.GenericResponse
import com.benrostudios.syncure.data.repositories.auth.AuthRepo

class AuthViewModel(private val authRepo: AuthRepo) : ViewModel() {

    suspend fun login(username: String, password: String): LiveData<GenericResponse> {
        return authRepo.signIn(username, password)
    }

}