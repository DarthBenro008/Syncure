package com.benrostudios.syncure.di.modules

import com.benrostudios.syncure.data.network.AuthService
import org.koin.dsl.module

val networkModule = module {
    single { AuthService() }
}