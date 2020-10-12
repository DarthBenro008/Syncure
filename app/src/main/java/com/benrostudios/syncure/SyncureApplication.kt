package com.benrostudios.syncure

import android.app.Application
import com.benrostudios.syncure.di.appComponent
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class SyncureApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@SyncureApplication)
            modules(appComponent)
        }

    }
}