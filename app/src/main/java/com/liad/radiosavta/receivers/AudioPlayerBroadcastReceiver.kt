package com.liad.radiosavta.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.liad.radiosavta.services.PlayMusicService
import com.liad.radiosavta.utils.Constants.IS_PLAYING
import com.liad.radiosavta.utils.Constants.SONG_NAME
import com.liad.radiosavta.utils.NotificationManager
import com.liad.radiosavta.utils.extension.toast

class AudioPlayerBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let { notNullContext ->
            intent?.let {
            toast(context , "${it.getBooleanExtra(IS_PLAYING , false)}")
                NotificationManager(notNullContext).showNotification(
                    it.getStringExtra(SONG_NAME),
                    !it.getBooleanExtra(IS_PLAYING, false)
                )
            }
        }
        context?.startService(Intent(context, PlayMusicService::class.java))
    }
}