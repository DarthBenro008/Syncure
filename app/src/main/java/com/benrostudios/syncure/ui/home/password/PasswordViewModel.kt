package com.benrostudios.syncure.ui.home.password

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.benrostudios.syncure.data.network.response.GenericResponse
import com.benrostudios.syncure.data.repositories.password.PasswordRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PasswordViewModel(private val passwordRepo: PasswordRepo) : ViewModel() {

    val passwordResponse = MutableLiveData<GenericResponse>()

    suspend fun getPasswords() {
        passwordRepo.getPasswords().observeForever {
            passwordResponse.postValue(it)
        }
    }


    suspend fun addPassword(passwordTitle: String, password: String): LiveData<GenericResponse> {
        return passwordRepo.addPassword(passwordTitle, password)
    }

    suspend fun removePassword(id: String): LiveData<GenericResponse> {
        return passwordRepo.removePassword(id)
    }


}