package com.application.nuntium

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.application.nuntium.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class app:Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@app)
            androidLogger(Level.DEBUG)
            modules (listOf(viewModelModule, repositoryModule, netModule, apiModule,dataBaseModule) )
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

}