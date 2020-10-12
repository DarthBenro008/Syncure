package com.benrostudios.syncure.di.modules


import com.benrostudios.syncure.data.repositories.auth.AuthRepo
import com.benrostudios.syncure.data.repositories.auth.AuthRepoImpl
import com.benrostudios.syncure.data.repositories.password.PasswordRepo
import com.benrostudios.syncure.data.repositories.password.PasswordRepoImpl
import com.benrostudios.syncure.utils.SharedPrefManager
import org.koin.dsl.module

val repositoryModule = module {
    single { SharedPrefManager(get()) }
    single<AuthRepo> { AuthRepoImpl(get(), get()) }
    single<PasswordRepo> {PasswordRepoImpl(get())}
}