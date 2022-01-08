package com.gyan.ambulanceschedule

import androidx.lifecycle.*
import com.gyan.ambulanceschedule.data.Schedule
import com.gyan.ambulanceschedule.data.ScheduleDao
import kotlinx.coroutines.launch

class ScheduleViewModel(private val scheduleDao: ScheduleDao) : ViewModel() {

    val listOfDays = listOf<String>("sunday", "monday", "tuesday", "wednesday", "thursday", "friday", "saturday")

    val allSchedules : LiveData<List<Schedule>> = scheduleDao.getSchedules().asLiveData()

    private fun insertSchedule(schedule: Schedule){
        viewModelScope.launch {
            scheduleDao.insert(schedule)
        }
    }

    private fun getNewScheduleEntry(name: String, phone: String, startTime: String, endTime: String, day: String) : Schedule {
        return Schedule(
            name = name,
            phone = phone,
            startTime = startTime,
            endTime = endTime,
            day = day,
        )
    }

    fun addNewSchedule(name: String, phone: String, startTime: String, endTime: String, day: String) {
        val newSchedule = getNewScheduleEntry(name, phone, startTime, endTime, day)
        insertSchedule(newSchedule)
    }

    fun isEntryValid(name: String, phone: String, startTime: String, endTime: String, day: String) : Boolean{
        if (name.isBlank() || phone.isBlank() || startTime.isBlank() || endTime.isBlank() || day.isBlank())
            return false

        val lowercaseDay = day.lowercase()
        if (!listOfDays.contains(lowercaseDay))
            return false
        return true
    }
}

class ScheduleViewModelFactory(private val scheduleDao:ScheduleDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ScheduleViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ScheduleViewModel(scheduleDao) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}