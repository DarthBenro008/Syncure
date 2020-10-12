package com.benrostudios.syncure.data.repositories.password

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.benrostudios.syncure.data.network.PasswordService
import com.benrostudios.syncure.data.network.response.GenericResponse
import com.benrostudios.syncure.data.repositories.BaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PasswordRepoImpl(private val passwordService: PasswordService) : PasswordRepo, BaseRepository() {

    val _passwordsList = MutableLiveData<GenericResponse>()
    val _passwordResponse = MutableLiveData<GenericResponse>()

    override suspend fun getPasswords(): LiveData<GenericResponse> {
        return withContext(Dispatchers.IO){
            _passwordsList.postValue(
                safeApiCall(
                    call = {passwordService.getAllPasswords()},
                    error = "Unable to get password"
                )
            )
            return@withContext _passwordsList
        }
    }

    override suspend fun addPassword(
        passwordTitle: String,
        password: String
    ): LiveData<GenericResponse> {
        return withContext(Dispatchers.IO){
            _passwordResponse.postValue(
                safeApiCall(
                    call = {passwordService.addPassword(passwordTitle,password)},
                    error = "Unable to get password"
                )
            )
            return@withContext _passwordResponse
        }
    }

    override suspend fun removePassword(id: String): LiveData<GenericResponse> {
        return withContext(Dispatchers.IO){
            _passwordResponse.postValue(
                safeApiCall(
                    call = {passwordService.removePassword(id)},
                    error = "Unable to get password"
                )
            )
            return@withContext _passwordResponse
        }
    }
}