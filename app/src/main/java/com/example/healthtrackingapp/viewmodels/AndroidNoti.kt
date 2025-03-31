import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.healthtrackingapp.R

// Hàm mở rộng để hiển thị thông báo
fun Context.showNotification(
    channelId: String,
    notificationId: Int,
    title: String,
    content: String
) {
    val notificationManager = getSystemService(NotificationManager::class.java)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = "My Channel Name" // Tùy chỉnh tên kênh
        val descriptionText = "Description of my notification channel" // Tùy chỉnh mô tả kênh
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelId, name, importance).apply {
            description = descriptionText
        }
        notificationManager.createNotificationChannel(channel)
    }

    val builder = NotificationCompat.Builder(this, channelId)
        .setSmallIcon(R.drawable.ic_notification_settings_96)
        .setContentTitle(title)
        .setContentText(content)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setAutoCancel(true)

    notificationManager.notify(notificationId, builder.build())
}