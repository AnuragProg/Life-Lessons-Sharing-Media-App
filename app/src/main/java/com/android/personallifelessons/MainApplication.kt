package com.android.personallifelessons

import android.app.Application
import com.android.personallifelessons.di.appModule
import com.android.personallifelessons.di.repositoryModule
import com.android.personallifelessons.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MainApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidLogger(level= Level.DEBUG)
            modules(appModule, repositoryModule, viewModelModule)
            androidContext(applicationContext)
        }
    }
}