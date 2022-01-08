package com.gyan.ambulanceschedule.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Schedule (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name="name")
    val name: String,
    @ColumnInfo(name="phone")
    val phone: String,
    @ColumnInfo(name="startTime")
    val startTime: String,
    @ColumnInfo(name="endTime")
    val endTime : String,
    @ColumnInfo(name="day")
    val day: String,
)

fun Schedule.getFormattedSchedule() : String {
    return "${day.replaceFirstChar { it.uppercase() }}, $startTime - $endTime"
}