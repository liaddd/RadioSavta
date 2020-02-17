package com.liad.radiosavta

import android.app.Application
import com.liad.radiosavta.di.appModule
import org.koin.core.context.startKoin

class RadioSavtaApplication : Application() {

    companion object {
        lateinit var instance: Application
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        startKoin {
            modules(listOf(appModule))
        }
    }
}