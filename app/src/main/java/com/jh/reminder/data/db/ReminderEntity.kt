package com.jh.reminder.data.db

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "Reminder")
data class ReminderEntity(
    val desc: String,
    val time: Long,
    val isActive: Boolean,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
) : Parcelable