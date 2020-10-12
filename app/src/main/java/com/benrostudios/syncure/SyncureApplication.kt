package com.benrostudios.syncure

import android.app.Application
import com.benrostudios.syncure.di.appComponent
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class SyncureApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        val client = OkHttpClient.Builder()
            .addInterceptor(ChuckInterceptor(this@SyncureApplication))
            .build()
        startKoin {
            androidLogger()
            androidContext(this@SyncureApplication)
            koin.loadModules(appComponent)
            koin.createRootScope()
        }

    }
}