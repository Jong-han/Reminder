package com.jh.reminder.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jh.reminder.data.db.ReminderEntity
import com.jh.reminder.di.IoDispatcher
import com.jh.reminder.usecase.GetUseCase
import com.jh.reminder.usecase.UpdateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val getUseCase: GetUseCase,
    private val updateUseCase: UpdateUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    init {
        viewModelScope.launch(ioDispatcher) {
            getUseCase.getReminderList().collect {
                _reminderList.value = it
            }
        }
    }

    sealed class ViewEvent {
        object AddReminder: ViewEvent()
        class Complete(val reminderEntity: ReminderEntity, val isActive: Boolean): ViewEvent()
    }

    private val _viewEvent = MutableSharedFlow<ViewEvent>()
    val viewEvent: SharedFlow<ViewEvent> = _viewEvent

    private val _reminderList = MutableStateFlow<List<ReminderEntity>>(emptyList())
    val reminderList: StateFlow<List<ReminderEntity>> = _reminderList

    fun onAddReminder() {
        viewModelScope.launch {
            _viewEvent.emit(ViewEvent.AddReminder)
        }
    }

    fun updateReminder(reminderEntity: ReminderEntity) {
        viewModelScope.launch(ioDispatcher) {
            updateUseCase.updateReminder(reminderEntity.also { it.isActive = !it.isActive })
            _viewEvent.emit(ViewEvent.Complete(reminderEntity, reminderEntity.isActive))
        }
    }

}