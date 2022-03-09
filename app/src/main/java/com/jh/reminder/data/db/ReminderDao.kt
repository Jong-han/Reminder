package com.jh.reminder.data.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ReminderDao {

    @Query("SELECT * FROM REMINDER")
    fun getAll(): Flow<List<ReminderEntity>>

    @Query("SELECT * FROM REMINDER WHERE requestCode = :requestCode")
    fun getReminderByRequestCode(requestCode: Int): ReminderEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(reminderEntity: ReminderEntity)

    @Update
    fun update(reminderEntity: ReminderEntity)

}