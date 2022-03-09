package com.jh.reminder.ui.active

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jh.reminder.data.db.ReminderEntity
import com.jh.reminder.di.IoDispatcher
import com.jh.reminder.usecase.GetUseCase
import com.jh.reminder.usecase.UpdateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActiveViewModel @Inject constructor(
    private val updateUseCase: UpdateUseCase,
    private val getUseCase: GetUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    sealed class ViewEvent {
        object Complete: ViewEvent()
    }

    private val _viewEvent = MutableSharedFlow<ViewEvent>()
    val viewEvent: SharedFlow<ViewEvent> = _viewEvent

    private val _reminder = MutableStateFlow<ReminderEntity?>(null)
    val reminder: StateFlow<ReminderEntity?> = _reminder

    fun getReminder(requestCode: Int) {
        viewModelScope.launch(ioDispatcher) {
            _reminder.value = getUseCase.getReminderByRequestCode(requestCode)
        }
    }

    fun dismissReminder() {
        viewModelScope.launch(ioDispatcher) {
            reminder.value?.let { reminder ->
                updateUseCase.updateReminder(reminder.also { it.isActive = false })
                _viewEvent.emit(ViewEvent.Complete)
            }
        }
    }

}