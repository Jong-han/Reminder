package com.jh.reminder.usecase

import com.jh.reminder.data.db.ReminderEntity

interface InsertUseCase {
    fun addReminder(reminderEntity: ReminderEntity)
}