package com.liad.radiosavta.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.liad.radiosavta.services.PlayMusicService
import com.liad.radiosavta.utils.Constants

class AudioPlayerBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent) {
        val songName = intent.extras?.getString(Constants.SONG_NAME) ?: ""
        val isPlaying = intent.extras?.getBoolean(Constants.IS_PLAYING) ?: false
        val playMusicIntent = Intent(context, PlayMusicService::class.java)
        playMusicIntent.putExtra(Constants.SONG_NAME, songName)
        context?.startService(playMusicIntent)
    }
}