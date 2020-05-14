package com.liad.radiosavta.services

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.liad.radiosavta.R
import com.liad.radiosavta.RadioSavtaApplication
import com.liad.radiosavta.RadioSavtaApplication.Companion.pauseAudio
import com.liad.radiosavta.RadioSavtaApplication.Companion.playAudio
import com.liad.radiosavta.activities.MainActivity
import com.liad.radiosavta.receivers.AudioPlayerBroadcastReceiver
import com.liad.radiosavta.utils.Constants
import com.liad.radiosavta.utils.extension.toast

class PlayMusicService : Service() {


    private var mediaPlayer : MediaPlayer? = null

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        mediaPlayer = RadioSavtaApplication.mediaPlayer
        if (mediaPlayer == null){
            mediaPlayer = RadioSavtaApplication.initMediaPlayer()
        }
        Log.d("Liad", "PlayMusicService onCreate()")
    }

    // TODO Liad - refactor service
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {

        val songName = intent.extras?.getString(Constants.SONG_NAME)
        mediaPlayer?.let {
            toast(RadioSavtaApplication.instance , "PlayMusicService onStartCommand()")
            if (it.isPlaying) {
                showNotification(songName)
                pauseAudio()
            } else {
                showNotification(songName, true)
                playAudio()
            }
        }
        return START_STICKY
    }


    private fun showNotification(title: String? = "", isPlaying: Boolean = false) {
        // intent notification clicked
        val notificationClickedIntent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        // action for notification click
        val notificationClickedPendingIntent =
            PendingIntent.getActivity(this, 0, notificationClickedIntent, 0)

        val playClickedIntent = Intent(this, AudioPlayerBroadcastReceiver::class.java)

        playClickedIntent.putExtra(Constants.SONG_NAME, title)
        playClickedIntent.putExtra(Constants.IS_PLAYING, !isPlaying)

        val playActionIntent = PendingIntent.getBroadcast(
            this,
            0,
            playClickedIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.savta_rounded_logo)
        val notification = NotificationCompat.Builder(this, "1")
            .setSmallIcon(R.drawable.savta_rounded_logo)
            .setContentTitle(title)
            .setContentText(null)
            .setLargeIcon(bitmap)
            .addAction(
                if (isPlaying) R.drawable.ic_pause_black_24dp else R.drawable.ic_play_arrow_black_24dp,
                null,
                playActionIntent
            )
            .setStyle(
                androidx.media.app.NotificationCompat.MediaStyle().setShowActionsInCompactView()
            )
            .setContentIntent(notificationClickedPendingIntent)
            .setDefaults(Notification.DEFAULT_LIGHTS or Notification.DEFAULT_SOUND)
            .setVibrate(longArrayOf(0L))
            .setChannelId(Constants.NOTIFICATION_CHANNEL_ID)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setOnlyAlertOnce(true)
            .build()

        if (isPlaying) {
            startForeground(1, notification)
        } else {
            //stopForeground(false)
            NotificationManagerCompat.from(this).notify(1, notification)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        toast(RadioSavtaApplication.instance , "PlayMusicService Destroyed!")
        Log.e("Liad", "Service Destroyed!")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            stopForeground(false)
        }
        mediaPlayer?.apply { stop(); release() }

    }

}