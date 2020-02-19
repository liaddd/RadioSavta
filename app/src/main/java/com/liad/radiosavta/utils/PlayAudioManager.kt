package com.liad.radiosavta.utils

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri

class PlayAudioManager {

    companion object {
        var mediaPlayer: MediaPlayer? = null
            private set

        fun initMediaPlayer(context: Context): MediaPlayer? {
            if (mediaPlayer == null) {
                mediaPlayer = MediaPlayer.create(context, Uri.parse(Constants.PLAY_URL))
            }
            return mediaPlayer
        }

        fun playAudio() {
            mediaPlayer?.setOnCompletionListener {
                closeMediaPlayer()
            }
            mediaPlayer?.start()
        }

        private fun closeMediaPlayer() {
            if (mediaPlayer != null) {
                try {
                    mediaPlayer?.reset()
                    mediaPlayer?.release()
                    mediaPlayer = null
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}