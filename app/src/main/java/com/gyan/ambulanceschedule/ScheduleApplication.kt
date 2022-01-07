package com.gyan.ambulanceschedule

import android.app.Application
import com.gyan.ambulanceschedule.data.ScheduleRoomDatabase

class ScheduleApplication : Application() {
    val database : ScheduleRoomDatabase by lazy {
        ScheduleRoomDatabase.getDatabase(this)
    }
}