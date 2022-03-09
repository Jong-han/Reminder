package com.jh.reminder.ui

import android.content.Intent
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
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val listFragment = supportFragmentManager.fragments[0].childFragmentManager.fragments[0] as ListFragment
        listFragment.navigateActiveFragment()
    }
}