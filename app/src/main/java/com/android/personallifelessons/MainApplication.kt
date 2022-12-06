package com.android.personallifelessons

import android.app.Application
import com.android.personallifelessons.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MainApplication: Application(), KoinComponent {

    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidLogger(level= Level.DEBUG)
            androidContext(applicationContext)
            workManagerFactory()
            modules(
                appModule,
                networkModule,
                repositoryModule,
                viewModelModule,
                workerModule,
            )
        }
    }
}