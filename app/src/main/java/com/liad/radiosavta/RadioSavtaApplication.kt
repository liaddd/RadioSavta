package com.liad.radiosavta

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.liad.radiosavta.di.appModule
import com.liad.radiosavta.utils.Constants
import org.koin.core.context.startKoin

class RadioSavtaApplication : Application() {

    companion object {
        lateinit var instance: Application
            private set
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        instance = this
        startKoin {
            modules(listOf(appModule))
        }
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "RadioSavta Channel"
            val descriptionText = "Radio Savta audio controllers notification"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel =
                NotificationChannel(Constants.NOTIFICATION_CHANNEL_ID, name, importance).apply {
                    description = descriptionText
                }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            channel.setSound(null, null)
            channel.description = "no sound"
            channel.enableVibration(false)
            notificationManager.createNotificationChannel(channel)
        }
    }
}