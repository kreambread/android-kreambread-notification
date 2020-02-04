package com.kreambread.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.text.format.DateFormat
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import java.util.*

const val notificationChannelId = "KREAMBREAD_NOTIFICATION_ID"
const val notificationChannelName = "Kreambread Notification"

object KreamNotification {

    fun show(context: Context, title: String, text: String) {
        val notificationId = 12
        val timestamp = System.currentTimeMillis()

        val remoteView = RemoteViews(context.applicationContext.packageName, R.layout.layout_notification).apply {
            setTextViewText(R.id.text_title, title)
            setTextViewText(R.id.text_description, text)
            setTextViewText(R.id.text_time, dateToString(Date(timestamp)))
        }

        val pendingIntent = PendingIntent.getActivity(context, notificationId, Intent(context, SecondActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }, PendingIntent.FLAG_UPDATE_CURRENT)

        val style = Notification.BigPictureStyle().bigPicture(BitmapFactory.decodeResource(context.resources, R.drawable.sample))

        val actions = arrayOf(Notification.Action(R.drawable.ic_notification, "Action1", pendingIntent),
            Notification.Action(R.drawable.ic_notification, "Action2", pendingIntent),
            Notification.Action(R.drawable.ic_notification, "Action3", pendingIntent))

        val builder = Notification.Builder(context)
            .setContentTitle(title)
            .setContentText(text)
            .setWhen(timestamp)
            .setSmallIcon(R.drawable.ic_notification)
            .setColor(context.getColor(R.color.colorNotification))
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setCustomContentView(remoteView)
            .setShowWhen(true)
            .setStyle(style)
            .setActions(*actions)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(context)
            builder.setChannelId(notificationChannelId)
        }

        (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).notify(notificationId, builder.build())
    }

    private fun dateToString(date: Date): String {
        var ret = ""
        runCatching {
            ret = DateFormat.format("kk:mm", date).toString()
        }

        return ret
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(context: Context) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        var notificationChannel: NotificationChannel? =
            notificationManager.getNotificationChannel(notificationChannelId)

        if (notificationChannel == null) {
            notificationChannel = NotificationChannel(notificationChannelId, notificationChannelName, NotificationManager.IMPORTANCE_DEFAULT).apply {
                setShowBadge(true)
            }
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }
}