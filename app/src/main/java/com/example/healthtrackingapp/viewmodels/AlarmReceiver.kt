package com.example.healthtrackingapp.viewmodels

import android.annotation.SuppressLint
import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.healthtrackingapp.AlarmActivity
import com.example.healthtrackingapp.R

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val alarmId = intent.getLongExtra("ALARM_ID", -1)
        val alarmLabel = intent.getStringExtra("ALARM_LABEL") ?: "Báo thức"

        when (intent.action) {
            "DISMISS_ALARM" -> dismissAlarm(context)
            else -> showFullScreenNotification(context, alarmId, alarmLabel)
        }
    }
    val alarmSound = Uri.parse("android.resource://com.example.healthtrackingapp/raw/iphone_sound")

    @SuppressLint("ServiceCast")
    private fun showFullScreenNotification(context: Context, alarmId: Long, alarmLabel: String) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Tạo Notification Channel nếu cần
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "alarm_channel",
                "Báo Thức",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Kênh thông báo báo thức"
                enableVibration(true)
            }
            notificationManager.createNotificationChannel(channel)
        }

        // Intent mở AlarmActivity
        val alarmIntent = Intent(context, AlarmActivity::class.java).apply {
            putExtra("ALARM_ID", alarmId)
            putExtra("ALARM_LABEL", alarmLabel)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        val alarmPendingIntent = PendingIntent.getActivity(context, alarmId.toInt(), alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        // Intent tắt báo thức
        val dismissIntent = Intent(context, AlarmReceiver::class.java).apply {
            action = "DISMISS_ALARM"
            putExtra("ALARM_ID", alarmId)
        }
        val dismissPendingIntent = PendingIntent.getBroadcast(context, (alarmId + 200).toInt(), dismissIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        // Âm thanh báo thức
//        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)


        // Tạo Notification
        val notification = NotificationCompat.Builder(context, "alarm_channel")
            .setSmallIcon(R.drawable.ic_notification_settings_96)
            .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.ic_notification_settings_96))
            .setContentTitle("⏰ Báo Thức!")
            .setContentText(alarmLabel)
            .setStyle(NotificationCompat.BigTextStyle().bigText("$alarmLabel\nNhấn để tắt hoặc vuốt để bỏ qua"))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setFullScreenIntent(alarmPendingIntent, true)
            .setAutoCancel(true)
            .setOngoing(true)
            .setSound(alarmSound)
            .setVibrate(longArrayOf(0, 500, 1000, 500, 1000))
            .addAction(R.drawable.ic_close_90, "Tắt báo thức", dismissPendingIntent)
            .setTimeoutAfter(300000) // Tự động tắt sau 5 phút nếu không có tương tác
            .build()

        // Hiển thị notification
        notificationManager.notify(alarmId.toInt(), notification)
        val mediaPlayer = MediaPlayer.create(context, alarmSound)
        mediaPlayer.start()
    }

    private fun dismissAlarm(context: Context) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancelAll()
        val mediaPlayer = MediaPlayer.create(context, alarmSound)
        mediaPlayer.release()
    }
}
