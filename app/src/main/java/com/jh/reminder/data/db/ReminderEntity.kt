package com.jh.reminder.data.db

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "Reminder")
data class ReminderEntity(
    var desc: String,
    var time: Long,
    var isActive: Boolean,
    var requestCode: Int,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
) : Parcelable