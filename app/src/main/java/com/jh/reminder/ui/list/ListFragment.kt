package com.jh.reminder.ui.list

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.jh.reminder.receiver.AlarmReceiver
import com.jh.reminder.BR
import com.jh.reminder.R
import com.jh.reminder.base.BaseFragment
import com.jh.reminder.data.db.ReminderEntity
import com.jh.reminder.databinding.FragmentListBinding
import com.jh.reminder.ext.repeatOnStarted
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class ListFragment: BaseFragment<FragmentListBinding, ListViewModel>() {

    override val viewModel: ListViewModel by viewModels()

    override val bindingVariable: Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_list

    private val listAdapter by lazy { ListAdapter(onClickReminder, onClickSwitch) }

    private var flag = true

    override fun initViewsAndEvents() {

        dataBinding.rvReminder.adapter = listAdapter

        repeatOnStarted {
            viewModel.reminderList.collect {
                listAdapter.submitList(it)
            }
        }

        repeatOnStarted {
            viewModel.viewEvent.collect {
                when (it) {
                    ListViewModel.ViewEvent.AddReminder -> {
                        if (flag) {
                            findNavController().navigate(R.id.action_listFragment_to_settingFragment)
                            flag = false
                        }
                    }
                    is ListViewModel.ViewEvent.Complete -> {
                        if (it.isActive)
                            setAlarm(it.reminderEntity)
                        else
                            cancelAlarm(it.reminderEntity)
                    }
                }
            }
        }
    }

    private val onClickReminder: (Int)->Unit = { position ->
        val item = listAdapter.currentList[position]
        val action = ListFragmentDirections.actionListFragmentToSettingFragment(item)
        findNavController().navigate(action)
    }

    private val onClickSwitch: (Int)->Unit = { position ->
        val item = listAdapter.currentList[position]
        viewModel.updateReminder(item)
    }

    private fun cancelAlarm(target: ReminderEntity) {
        val alarmManager = requireActivity().getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(requireContext(), AlarmReceiver::class.java)
        val requestCode = target.requestCode
        val pendingIntent = PendingIntent.getBroadcast(requireContext(), requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        alarmManager.cancel(pendingIntent)
    }

    private fun setAlarm(target: ReminderEntity) {
        val alarmManager = requireActivity().getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(requireContext(), AlarmReceiver::class.java)
        val requestCode = target.requestCode
        val pendingIntent = PendingIntent.getBroadcast(requireContext(), requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val refreshTriggerTime = if ( target.time > System.currentTimeMillis() ) {
            target.time
        }
        else {
            target.time + 1000 * 60 * 60 * 24
        }
        alarmManager.set(
            AlarmManager.RTC_WAKEUP,
            refreshTriggerTime,
            pendingIntent
        )
    }

    fun navigateActiveFragment(requestCode: Int) {
        val action = ListFragmentDirections.actionListFragmentToActiveFragment(requestCode)
        findNavController().navigate(action)
    }

    override fun onResume() {
        super.onResume()
        flag = true
    }

}