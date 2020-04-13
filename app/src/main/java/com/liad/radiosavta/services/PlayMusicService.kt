package com.liad.radiosavta.services

import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import com.liad.radiosavta.managers.PlayAudioManager


// TODO Liad - refactor
class PlayMusicService : Service() {

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.i("Liad", "inside onCreate if statement")
        }
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        PlayAudioManager.initMediaPlayer()
        Log.d("Liad", "onStartCommand")

        PlayAudioManager.mediaPlayer?.let {
            if (it.isPlaying) {
                PlayAudioManager.closeMediaPlayer()
            } else {
                PlayAudioManager.playAudio()
            }
        }
        return START_STICKY
    }

    // TODO Liad - refactor and fix (service destroyed automatically after few seconds)
    override fun onDestroy() {
        super.onDestroy()
        Log.e("Liad", "Service Destroyed!")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            stopForeground(true)
        }
    }

}