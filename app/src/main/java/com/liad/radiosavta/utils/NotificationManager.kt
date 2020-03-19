package com.liad.radiosavta.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.liad.radiosavta.R
import com.liad.radiosavta.activities.MainActivity
import com.liad.radiosavta.receivers.AudioPlayerBroadcastReceiver
import com.liad.radiosavta.utils.Constants.IS_PLAYING
import com.liad.radiosavta.utils.Constants.NOTIFICATION_CHANNEL_ID
import com.liad.radiosavta.utils.Constants.SONG_NAME

class NotificationManager(val context: Context) {

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "test"
            val descriptionText = "testing"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun showNotification(title: String, isPlaying: Boolean = false) {
        createNotificationChannel()

        // intent notification clicked
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        // action for notification click
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

        val playClickedIntent = Intent(context, AudioPlayerBroadcastReceiver::class.java)
        playClickedIntent.putExtra(SONG_NAME, title)
        playClickedIntent.putExtra(IS_PLAYING, isPlaying)
        val playActionIntent = PendingIntent.getBroadcast(
            context,
            0,
            playClickedIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        // custom notification view
        /*val collapsedView = RemoteViews(context.packageName, R.layout.media_notification_collapsed)
        collapsedView.setTextViewText(R.id.notification_title , title)

        val expendedView = RemoteViews(context.packageName, R.layout.media_notification_expended)
        expendedView.setTextViewText(R.id.notification_title , title)
        expendedView.setOnClickPendingIntent(R.id.notification_play_button , playActionIntent)*/


        val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.savta_rounded_logo)
        val notification = NotificationCompat.Builder(context, "1")
            .setSmallIcon(R.drawable.savta_rounded_logo)
            //.setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.savta_rounded_logo))
            /*.setCustomContentView(collapsedView)
            .setCustomBigContentView(expendedView)*/
            .setContentTitle(title)
            .setContentText(null)
            .setLargeIcon(bitmap)
            .addAction(
                if (isPlaying) R.drawable.ic_pause_black_24dp else R.drawable.ic_play_arrow_black_24dp,
                null,
                playActionIntent
            )
            .setStyle(/*NotificationCompat.DecoratedCustomViewStyle()*/androidx.media.app.NotificationCompat.MediaStyle()
                .setShowActionsInCompactView()
            )
            .setContentIntent(pendingIntent)
            .setSound(null)
            .setVibrate(null)
            .setChannelId(NOTIFICATION_CHANNEL_ID)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        NotificationManagerCompat.from(context).notify(1, notification)
    }
}