package com.jh.reminder.ui.active

import androidx.fragment.app.viewModels
import com.jh.reminder.BR
import com.jh.reminder.R
import com.jh.reminder.base.BaseFragment
import com.jh.reminder.databinding.FragmentActiveBinding

class ActiveFragment: BaseFragment<FragmentActiveBinding, ActiveViewModel>() {

    override val viewModel: ActiveViewModel by viewModels()

    override val bindingVariable: Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_active

    override fun initViewsAndEvents() {

    }
}