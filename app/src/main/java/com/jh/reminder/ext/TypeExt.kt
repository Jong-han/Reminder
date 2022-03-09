package com.jh.reminder.ext

import java.util.*

fun Long.longToCalendar(): Calendar {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this
    return calendar
}