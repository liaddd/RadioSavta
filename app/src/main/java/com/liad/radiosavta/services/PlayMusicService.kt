package com.liad.radiosavta.services

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.media.session.PlaybackState.ACTION_PLAY
import android.os.IBinder
import android.util.Log
import com.liad.radiosavta.utils.PlayAudioManager
import com.liad.radiosavta.utils.extension.log

class PlayMusicService : Service(), MediaPlayer.OnPreparedListener {

    private var mediaPlayer : MediaPlayer? = null

    override fun onBind(intent: Intent?): IBinder? = null

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

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
    }

    override fun onPrepared(mp: MediaPlayer?) {

    }

}