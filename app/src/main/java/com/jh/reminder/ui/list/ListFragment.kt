package com.jh.reminder.ui.list

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.jh.reminder.BR
import com.jh.reminder.R
import com.jh.reminder.base.BaseFragment
import com.jh.reminder.databinding.FragmentListBinding
import com.jh.reminder.ext.repeatOnStarted
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class ListFragment: BaseFragment<FragmentListBinding, ListViewModel>() {

    override val viewModel: ListViewModel by viewModels()

    override val bindingVariable: Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_list

    private val listAdapter by lazy { ListAdapter() }

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
                        findNavController().navigate(R.id.action_listFragment_to_settingFragment)
                    }
                    ListViewModel.ViewEvent.ClickReminder -> {
                        findNavController().navigate(R.id.action_listFragment_to_settingFragment)
                    }
                    ListViewModel.ViewEvent.ChangeReminderState -> {
                    }
                }
            }
        }

    }
}