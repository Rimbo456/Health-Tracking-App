/*
package com.example.healthtrackingapp.data.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.healthtrackingapp.data.models.HealthJournal
import com.example.healthtrackingapp.data.models.HealthNote
import com.example.healthtrackingapp.data.models.HeartRate
import com.example.healthtrackingapp.data.models.Mood
import com.example.healthtrackingapp.data.models.Notification
import com.example.healthtrackingapp.data.models.Nutrition
import com.example.healthtrackingapp.data.models.SleepRecord
import com.example.healthtrackingapp.data.models.User
import com.example.healthtrackingapp.data.models.Workout

@Database(
    entities = [
        User::class, HealthJournal::class, HeartRate::class,
        SleepRecord::class, Workout::class, HealthNote::class,
        Mood::class, Nutrition::class, Notification::class
    ],
    version = 1,
    exportSchema = false
)
abstract class HealthDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun healthJournalDao(): HealthJournalDao
    abstract fun heartRateDao(): HeartRateDao
    abstract fun sleepRecordDao(): SleepRecordDao
    abstract fun workoutDao(): WorkoutDao
    abstract fun healthNoteDao(): HealthNoteDao
    abstract fun moodDao(): MoodDao
    abstract fun nutritionDao(): NutritionDao
    abstract fun notificationDao(): NotificationDao

    companion object {
        @Volatile
        private var INSTANCE: HealthDatabase? = null

        fun getInstance(context: Context): HealthDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HealthDatabase::class.java,
                    "health_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
*/
