package com.jh.reminder.data.repository

import com.jh.reminder.data.db.ReminderEntity
import kotlinx.coroutines.flow.Flow

interface LocalRepository {
    fun getReminderList() : Flow<List<ReminderEntity>>
    fun addReminder(reminderEntity: ReminderEntity)
    fun updateReminder(reminderEntity: ReminderEntity)
    fun switchReminder(reminderEntity: ReminderEntity)
    fun getReminderByRequestCode(requestCode: Int): ReminderEntity
}