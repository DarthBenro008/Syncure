package com.benrostudios.syncure.di

import com.benrostudios.syncure.di.modules.networkModule
import com.benrostudios.syncure.di.modules.repositoryModule
import com.benrostudios.syncure.di.modules.viewModelModule

val appComponent = listOf(
    networkModule,
    repositoryModule,
    viewModelModule
)