package com.benrostudios.syncure.data.repositories.password

import androidx.lifecycle.LiveData
import com.benrostudios.syncure.data.models.Password
import com.benrostudios.syncure.data.network.response.GenericResponse
import java.util.concurrent.LinkedBlockingDeque

interface PasswordRepo {
    suspend fun getPasswords(): LiveData<GenericResponse>
    suspend fun addPassword(passwordTitle: String, password: String): LiveData<GenericResponse>
    suspend fun removePassword(id: String): LiveData<GenericResponse>
}