package com.jh.reminder.ui.setting

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.jh.reminder.BR
import com.jh.reminder.R
import com.jh.reminder.base.BaseFragment
import com.jh.reminder.databinding.FragmentSettingBinding
import com.jh.reminder.ext.repeatOnStarted
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class SettingFragment: BaseFragment<FragmentSettingBinding, SettingViewModel>() {

    override val viewModel: SettingViewModel by viewModels()

    override val bindingVariable: Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_setting

    override fun initViewsAndEvents() {

        repeatOnStarted {
            viewModel.viewEvent.collect {
                when (it) {
                    SettingViewModel.ViewEvent.CommitResult -> {
                        val desc = dataBinding.etReminderDesc.text.toString()
                        val hour = dataBinding.timePicker.hour
                        val minute = dataBinding.timePicker.minute
                        viewModel.addOrUpdateReminder(desc, hour, minute, true)
                    }
                    SettingViewModel.ViewEvent.Complete -> {
                        findNavController().navigate(R.id.action_settingFragment_to_listFragment)
                    }
                }
            }
        }

    }
}