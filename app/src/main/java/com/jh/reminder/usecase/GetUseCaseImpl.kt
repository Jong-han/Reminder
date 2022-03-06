package com.jh.reminder.usecase

import com.jh.reminder.data.db.ReminderEntity
import com.jh.reminder.data.repository.LocalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUseCaseImpl @Inject constructor(private val localRepository: LocalRepository): GetUseCase {
    override fun getReminderList(): Flow<List<ReminderEntity>> {
        return localRepository.getReminderList()
    }
}