package com.liad.radiosavta.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.liad.radiosavta.RadioSavtaApplication
import com.liad.radiosavta.services.PlayMusicService
import com.liad.radiosavta.utils.Constants
import com.liad.radiosavta.utils.extension.sendEvent

class AudioPlayerBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent) {
        val songName = intent.extras?.getString(Constants.SONG_NAME) ?: ""
        val isPlaying = intent.extras?.getBoolean(Constants.IS_PLAYING) ?: false

        RadioSavtaApplication.sTracker?.sendEvent(action = if(isPlaying) "Play" else "Pause")

        val testIntent = Intent(Constants.LOCAL_BROADCAST_UPDATE)
        testIntent.putExtra(Constants.IS_PLAYING, isPlaying)
        context?.let {
            LocalBroadcastManager.getInstance(it).sendBroadcast(testIntent)
        }

        val playMusicIntent = Intent(context, PlayMusicService::class.java)
        playMusicIntent.putExtra(Constants.SONG_NAME, songName)
        context?.startService(playMusicIntent)
    }
}