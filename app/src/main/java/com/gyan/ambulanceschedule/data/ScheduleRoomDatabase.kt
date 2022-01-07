package com.gyan.ambulanceschedule.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Schedule::class], version = 1, exportSchema = false)
abstract class ScheduleRoomDatabase : RoomDatabase(){

    abstract fun scheduleDao() : ScheduleDao


    companion object{
        @Volatile
        private var INSTANCE : ScheduleRoomDatabase? = null

        fun getDatabase(context: Context) : ScheduleRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ScheduleRoomDatabase::class.java,
                    "item_database"
                )
                    // Wipes and rebuilds instead of migrating if no Migration object.
                    // Migration is not part of this codelab.
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}