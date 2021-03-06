package com.liad.radiosavta

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.MediaPlayer
import android.os.Build
import com.google.android.gms.analytics.GoogleAnalytics
import com.google.android.gms.analytics.Tracker
import com.liad.radiosavta.di.appModule
import com.liad.radiosavta.utils.Constants
import org.koin.core.context.startKoin


class RadioSavtaApplication : Application() {

    companion object {
        lateinit var sAnalytics : GoogleAnalytics
        var sTracker : Tracker? = null

        fun getDefaultTracker(): Tracker? {
            if (sTracker == null){
                sTracker = sAnalytics.newTracker(R.xml.global_tracker)
            }
            return sTracker
        }

        lateinit var instance: Application
            private set

        @Volatile
        var mediaPlayer: MediaPlayer? = null
            get() {
                if (field == null) {
                    field = MediaPlayer()
                    try {
                        mediaPlayer?.setDataSource(Constants.PLAY_URL)
                        mediaPlayer?.prepare()
                    } catch (e: IllegalArgumentException) {
                        e.printStackTrace()
                    }
                }
                return field
            }

        fun initMediaPlayer(): MediaPlayer? {
            if (mediaPlayer == null) {
                mediaPlayer = MediaPlayer()
                try {
                    mediaPlayer?.setDataSource(Constants.PLAY_URL)
                    mediaPlayer?.prepare()
                } catch (e: IllegalArgumentException) {
                    e.printStackTrace()
                }
            }
            return mediaPlayer
        }

        fun playAudio() {
            if (mediaPlayer == null) {
                mediaPlayer = initMediaPlayer()
            }
            mediaPlayer?.start()
        }

        fun pauseAudio() {
            if (mediaPlayer != null) {
                try {
                    mediaPlayer?.pause()
                    //mediaPlayer = null
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        sAnalytics = GoogleAnalytics.getInstance(this)
        sTracker = getDefaultTracker()
        sTracker?.enableAutoActivityTracking(true)
        sTracker?.enableExceptionReporting(true)

        createNotificationChannel()
        initMediaPlayer()

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