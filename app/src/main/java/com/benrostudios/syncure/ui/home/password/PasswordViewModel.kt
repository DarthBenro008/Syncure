package com.benrostudios.syncure.ui.home.password

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.benrostudios.syncure.data.network.response.GenericResponse
import com.benrostudios.syncure.data.repositories.password.PasswordRepo

class PasswordViewModel(private val passwordRepo: PasswordRepo) : ViewModel() {

    suspend fun getPasswords(): LiveData<GenericResponse> {
        return passwordRepo.getPasswords()
    }

    suspend fun addPassword(passwordTitle: String, password: String): LiveData<GenericResponse> {
        return passwordRepo.addPassword(passwordTitle, password)
    }

    suspend fun removePassword(id: String): LiveData<GenericResponse> {
        return passwordRepo.removePassword(id)
    }


}