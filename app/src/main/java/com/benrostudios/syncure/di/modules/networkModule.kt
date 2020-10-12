package com.benrostudios.syncure.di.modules

import com.benrostudios.syncure.data.network.AuthService
import com.benrostudios.syncure.data.network.PasswordService
import org.koin.dsl.module

val networkModule = module {
    single { AuthService() }
    single { PasswordService(get(),get()) }
}