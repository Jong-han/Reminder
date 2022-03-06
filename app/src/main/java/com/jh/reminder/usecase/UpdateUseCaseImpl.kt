package com.jh.reminder.usecase

import com.jh.reminder.data.db.ReminderDao
import com.jh.reminder.data.db.ReminderEntity
import javax.inject.Inject

class UpdateUseCaseImpl @Inject constructor(private val reminderDao: ReminderDao): UpdateUseCase {
    override fun updateReminder(reminderEntity: ReminderEntity) {
        reminderDao.update(reminderEntity)
    }
}