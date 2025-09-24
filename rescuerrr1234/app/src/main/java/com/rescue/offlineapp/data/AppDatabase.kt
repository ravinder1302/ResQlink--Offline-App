package com.rescue.offlineapp.data

import android.content.Context
import androidx.room.*
import androidx.room.TypeConverters

@Database(entities = [AlertEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun alertDao(): AlertDao
    
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "rescue_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

class Converters {
    @TypeConverter
    fun fromPriority(priority: AlertMessage.Priority): String {
        return priority.name
    }
    
    @TypeConverter
    fun toPriority(priority: String): AlertMessage.Priority {
        return AlertMessage.Priority.valueOf(priority)
    }
}
