package com.jh.reminder.data.repository

import com.jh.reminder.data.db.ReminderDao
import com.jh.reminder.data.db.ReminderEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalRepositoryImpl @Inject constructor(private val reminderDao: ReminderDao): LocalRepository {
    override suspend fun getReminderList(): Flow<List<ReminderEntity>> {
        return reminderDao.getAll()
    }

    override suspend fun addReminder(reminderEntity: ReminderEntity) {
        reminderDao.insert(reminderEntity)
    }

    override suspend fun updateReminder(reminderEntity: ReminderEntity) {
        reminderDao.update(reminderEntity)
    }

    override suspend fun switchReminder(reminderEntity: ReminderEntity) {
        reminderDao.update(reminderEntity)
    }

}