package com.liad.radiosavta.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.liad.radiosavta.managers.NotificationManager
import com.liad.radiosavta.services.PlayMusicService
import com.liad.radiosavta.utils.Constants.IS_PLAYING
import com.liad.radiosavta.utils.Constants.SONG_NAME

class AudioPlayerBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let { notNullContext ->
            intent?.let {
            Log.i("Liad" , "${it.getBooleanExtra(IS_PLAYING , false)}")
                NotificationManager(notNullContext).showNotification(
                    it.getStringExtra(SONG_NAME),
                    it.getBooleanExtra(IS_PLAYING, false)
                )
            }
        }
        context?.startService(Intent(context, PlayMusicService::class.java))
    }
}