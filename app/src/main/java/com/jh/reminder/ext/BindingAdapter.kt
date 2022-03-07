package com.jh.reminder.ext

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
@BindingAdapter("time")
fun TextView.setTime(time: Long) {
    val sdf = SimpleDateFormat("hh:mm aa")
    val date = Date(time)
    text = sdf.format(date)
}