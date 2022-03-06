package com.jh.reminder.usecase

import com.jh.reminder.data.db.ReminderEntity

interface UpdateUseCase {
    fun updateReminder(reminderEntity: ReminderEntity)
}