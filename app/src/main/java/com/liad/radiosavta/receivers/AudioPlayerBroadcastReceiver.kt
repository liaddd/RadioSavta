package com.liad.radiosavta.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat
import com.liad.radiosavta.services.PlayMusicService
import com.liad.radiosavta.utils.Constants

class AudioPlayerBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent) {
        context?.let {
            val isPlaying = intent.extras?.getBoolean(Constants.IS_PLAYING) ?: false
            Log.i("Liad" , "isPlaying: $isPlaying")
            val playMusicIntent = Intent(context, PlayMusicService::class.java)
            ContextCompat.startForegroundService(it, playMusicIntent)
        }
    }
}