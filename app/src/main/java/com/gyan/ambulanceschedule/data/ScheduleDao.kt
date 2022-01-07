package com.gyan.ambulanceschedule.data

import android.content.ClipData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ScheduleDao {
    @Query("SELECT * from schedule ORDER BY name ASC")
    fun getSchedules(): Flow<List<Schedule>>

    @Query("SELECT * from schedule WHERE id = :id")
    fun getSchedule(id: Int): Flow<Schedule>

    // Specify the conflict strategy as IGNORE, when the user tries to add an
    // existing Item into the database Room ignores the conflict.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(schedule: Schedule)

    @Update
    suspend fun update(schedule: Schedule)

    @Delete
    suspend fun delete(schedule: Schedule)
}