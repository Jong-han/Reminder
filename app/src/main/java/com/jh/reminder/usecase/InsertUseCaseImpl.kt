package com.jh.reminder.usecase

import com.jh.reminder.data.db.ReminderDao
import com.jh.reminder.data.db.ReminderEntity
import com.jh.reminder.data.preference.AppPreference
import javax.inject.Inject

class InsertUseCaseImpl @Inject constructor(private val reminderDao: ReminderDao,
                                            private val pref: AppPreference): InsertUseCase {
    override fun addReminder(reminderEntity: ReminderEntity) {
        reminderDao.insert(reminderEntity)
    }

    override fun getLatestRequestKey(): Int {
        val result = pref.latestRequestKey
        pref.latestRequestKey = pref.latestRequestKey + 1
        return result
    }
}