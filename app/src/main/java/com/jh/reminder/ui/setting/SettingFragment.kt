package com.jh.reminder.ui.setting

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.jh.reminder.BR
import com.jh.reminder.R
import com.jh.reminder.base.BaseFragment
import com.jh.reminder.databinding.FragmentSettingBinding
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
            val calendar = longToCalendar(it.time)
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
                        findNavController().navigate(R.id.action_settingFragment_to_listFragment)
                    }
                }
            }
        }

    }

    private fun longToCalendar(time: Long): Calendar {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = time
        return calendar
    }
}