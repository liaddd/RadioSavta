package com.liad.radiosavta.utils

import android.media.MediaPlayer

class PlayAudioManager {

    companion object {
        var mediaPlayer: MediaPlayer? = null
            private set

        fun initMediaPlayer(): MediaPlayer? {
            if (mediaPlayer == null) {
                mediaPlayer = MediaPlayer()
                try {
                    mediaPlayer?.setDataSource(Constants.PLAY_URL)
                    mediaPlayer?.prepare()
                } catch (e: IllegalArgumentException) {
                    e.printStackTrace()
                }
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