package com.jh.reminder.usecase

import com.jh.reminder.data.db.ReminderEntity
import kotlinx.coroutines.flow.Flow

interface GetUseCase {
    fun getReminderList(): Flow<List<ReminderEntity>>
    fun getReminderByRequestCode(requestCode: Int): ReminderEntity
}