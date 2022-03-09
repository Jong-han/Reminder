package com.jh.reminder.ui.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jh.reminder.data.db.ReminderEntity
import com.jh.reminder.di.IoDispatcher
import com.jh.reminder.usecase.InsertUseCase
import com.jh.reminder.usecase.UpdateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val insertUseCase: InsertUseCase,
    private val updateUseCase: UpdateUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
): ViewModel() {

    sealed class ViewEvent {
        object CommitResult: ViewEvent()
        object Complete: ViewEvent()
    }

    private val _viewEvent = MutableSharedFlow<ViewEvent>()
    val viewEvent: SharedFlow<ViewEvent> = _viewEvent

    private lateinit var targetReminderEntity: ReminderEntity

    fun onCommitResult() {
        viewModelScope.launch {
            _viewEvent.emit(ViewEvent.CommitResult)
        }
    }

    fun addReminder(desc: String, hour: Int, minute: Int) {
        viewModelScope.launch {
            val time = timeToLong(hour, minute)
            val reminderEntity = ReminderEntity(desc, time, true, getNewRequestKey())
            withContext(ioDispatcher) {
                insertUseCase.addReminder(reminderEntity)
            }
            targetReminderEntity = reminderEntity
            _viewEvent.emit(ViewEvent.Complete)
        }
    }

    fun updateReminder(desc: String, hour: Int, minute: Int, reminderEntity: ReminderEntity) {
        viewModelScope.launch {
            val time = timeToLong(hour, minute)
            val target = reminderEntity.also {
                it.desc = desc
                it.time = time
            }
            withContext(ioDispatcher) {
                updateUseCase.updateReminder(target)
            }
            targetReminderEntity = target
            _viewEvent.emit(ViewEvent.Complete)
        }
    }

    private fun getNewRequestKey(): Int = insertUseCase.getLatestRequestKey()

    fun getTargetReminderEntity(): ReminderEntity = targetReminderEntity

    private fun timeToLong(hour: Int, minute: Int): Long {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.timeInMillis
    }
}