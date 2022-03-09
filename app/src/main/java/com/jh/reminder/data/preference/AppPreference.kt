package com.jh.reminder.data.preference

import android.content.Context
import android.content.SharedPreferences


class AppPreference constructor(private val context: Context ) {

    private val sharedPreferences: SharedPreferences by lazy { context.getSharedPreferences( "pref", 0 ) }

    var latestRequestKey: Int
        get() = sharedPreferences.getInt("latestRequestKey", 0)
        set(value) = sharedPreferences.edit().putInt("latestRequestKey", value).apply()

}