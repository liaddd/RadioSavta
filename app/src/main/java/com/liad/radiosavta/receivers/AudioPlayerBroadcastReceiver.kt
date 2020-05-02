package com.liad.radiosavta.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import com.liad.radiosavta.services.PlayMusicService
import com.liad.radiosavta.utils.Constants

class AudioPlayerBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent) {
        context?.let {
            val songName = intent.extras?.getString(Constants.SONG_NAME) ?: ""
            val playMusicIntent = Intent(context, PlayMusicService::class.java)
            playMusicIntent.putExtra(Constants.SONG_NAME, songName)
            ContextCompat.startForegroundService(it, playMusicIntent)
        }
    }
}