package com.jh.reminder.ui.active

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.jh.reminder.BR
import com.jh.reminder.R
import com.jh.reminder.base.BaseFragment
import com.jh.reminder.databinding.FragmentActiveBinding
import com.jh.reminder.ext.repeatOnStarted
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class ActiveFragment: BaseFragment<FragmentActiveBinding, ActiveViewModel>() {

    override val viewModel: ActiveViewModel by viewModels()

    override val bindingVariable: Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_active

    override fun initViewsAndEvents() {
        val args: ActiveFragmentArgs by navArgs()
        val target = args.requestCode
        viewModel.getReminder(target)

        repeatOnStarted {
            viewModel.viewEvent.collect {
                when (it) {
                    ActiveViewModel.ViewEvent.Complete -> {
                        findNavController().popBackStack()
                    }
                }
            }
        }

    }
}