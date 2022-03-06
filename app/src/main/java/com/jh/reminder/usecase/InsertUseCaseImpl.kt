package com.jh.reminder.usecase

import com.jh.reminder.data.db.ReminderDao
import com.jh.reminder.data.db.ReminderEntity
import javax.inject.Inject

class InsertUseCaseImpl @Inject constructor(private val reminderDao: ReminderDao): InsertUseCase {
    override fun addReminder(reminderEntity: ReminderEntity) {
        reminderDao.insert(reminderEntity)
    }
}