package com.jh.reminder.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jh.reminder.databinding.ActivityMainBinding
import com.jh.reminder.ui.list.ListFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var dataBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(dataBinding.root)

        showActiveFragment()

    }
    private fun showActiveFragment() {
        val requestCode = intent?.getIntExtra("asdf", -1)
        val listFragment = supportFragmentManager.fragments[0].childFragmentManager.fragments[0] as ListFragment
        if (requestCode != -1) {
            listFragment.navigateActiveFragment(requestCode ?: -1)
        }
    }
}