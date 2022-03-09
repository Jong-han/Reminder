package com.jh.reminder.ui.setting

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.jh.reminder.AlarmReceiver
import com.jh.reminder.BR
import com.jh.reminder.R
import com.jh.reminder.base.BaseFragment
import com.jh.reminder.data.db.ReminderEntity
import com.jh.reminder.databinding.FragmentSettingBinding
import com.jh.reminder.ext.longToCalendar
import com.jh.reminder.ext.repeatOnStarted
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import java.util.*

@AndroidEntryPoint
class SettingFragment: BaseFragment<FragmentSettingBinding, SettingViewModel>() {

    override val viewModel: SettingViewModel by viewModels()

    override val bindingVariable: Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_setting

    override fun initViewsAndEvents() {

        val args: SettingFragmentArgs by navArgs()
        val target = args.item

        target?.let {
            val calendar = it.time.longToCalendar()
            dataBinding.etReminderDesc.setText(it.desc)
            dataBinding.timePicker.hour = calendar.get(Calendar.HOUR_OF_DAY)
            dataBinding.timePicker.minute = calendar.get(Calendar.MINUTE)
        }

        repeatOnStarted {
            viewModel.viewEvent.collect {
                when (it) {
                    SettingViewModel.ViewEvent.CommitResult -> {
                        val desc = dataBinding.etReminderDesc.text.toString()
                        val hour = dataBinding.timePicker.hour
                        val minute = dataBinding.timePicker.minute
                        if (target == null)
                            viewModel.addReminder(desc, hour, minute)
                        else
                            viewModel.updateReminder(desc, hour, minute, target)
                    }
                    SettingViewModel.ViewEvent.Complete -> {
                        setAlarm(viewModel.getTargetReminderEntity())
                        findNavController().popBackStack()
                    }
                }
            }
        }

        dataBinding.containerRingtone.setOnClickListener {
            val intent = Intent(RingtoneManager.ACTION_RINGTONE_PICKER)
//            intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_NOTIFICATION)
//            intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, true)
//            intent.putExtra(RingtoneManager.EXTRA_RINGTONE_DEFAULT_URI,
//                RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            ringtoneResult.launch(intent)
        }

    }

    private fun setAlarm(target: ReminderEntity) {
        val alarmManager = requireActivity().getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(requireContext(), AlarmReceiver::class.java)
        intent.putExtra("asdf",target.requestCode)
        Log.i("asdf","target :: $target")
        val requestCode = target.requestCode
        val pendingIntent = PendingIntent.getBroadcast(
            requireContext(), requestCode, intent,
            PendingIntent.FLAG_UPDATE_CURRENT)

        val refreshCalendar = Calendar.getInstance()
        refreshCalendar.set(Calendar.HOUR_OF_DAY, dataBinding.timePicker.hour)
        refreshCalendar.set(Calendar.MINUTE, dataBinding.timePicker.minute)
        refreshCalendar.set(Calendar.SECOND, 0)
        refreshCalendar.set(Calendar.MILLISECOND, 0)

        val refreshTriggerTime = if ( refreshCalendar.timeInMillis > System.currentTimeMillis() ) {
            refreshCalendar.timeInMillis
        }
        else {
            refreshCalendar.timeInMillis + 1000 * 60 * 60 * 24
        }

        alarmManager.cancel(pendingIntent)

        alarmManager.set(
            AlarmManager.RTC_WAKEUP,
            refreshTriggerTime,
            pendingIntent
        )
    }

    private val ringtoneResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
    }

}