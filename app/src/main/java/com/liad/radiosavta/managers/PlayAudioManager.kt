package com.liad.radiosavta.managers

import android.media.MediaPlayer
import android.util.Log
import com.liad.radiosavta.utils.Constants

class PlayAudioManager {

    companion object {
        var mediaPlayer: MediaPlayer? = null
            private set

        fun initMediaPlayer(): MediaPlayer? {
            if (mediaPlayer == null) {
                mediaPlayer = MediaPlayer()
                try {
                    mediaPlayer?.setDataSource(
                        Constants.PLAY_URL
                    )
                    mediaPlayer?.prepare()
                } catch (e: IllegalArgumentException) {
                    e.printStackTrace()
                }
            }
            return mediaPlayer
        }

        fun playAudio() {
            /*mediaPlayer?.setOnCompletionListener {
                closeMediaPlayer()
            }*/
            mediaPlayer?.start()
            Log.d("Liad" , "playAudio occurred!")
        }

        fun closeMediaPlayer() {
            if (mediaPlayer != null) {
                try {
                    mediaPlayer?.reset()
                    mediaPlayer = null
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}