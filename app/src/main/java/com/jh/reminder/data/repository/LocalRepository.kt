package com.jh.reminder.data.repository

import com.jh.reminder.data.db.ReminderEntity
import kotlinx.coroutines.flow.Flow

interface LocalRepository {
    suspend fun getReminderList() : Flow<List<ReminderEntity>>
    suspend fun addReminder(reminderEntity: ReminderEntity)
    suspend fun updateReminder(reminderEntity: ReminderEntity)
    suspend fun switchReminder(reminderEntity: ReminderEntity)
}