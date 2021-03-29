package com.mateusmelodn.catimages

import android.app.Application
import com.mateusmelodn.catimages.core.config.appModule
import com.mateusmelodn.catimages.core.config.networkModule
import com.mateusmelodn.catimages.core.config.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * Custom application class to initialize Koin.
 */
class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(appModule, networkModule, viewModelModule)
        }
    }
}