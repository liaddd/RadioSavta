package com.liad.radiosavta.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.liad.radiosavta.utils.PlayAudioManager

class PlayMusicService : Service() {

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
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

}