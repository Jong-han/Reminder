package com.jh.reminder.di

import android.content.Context
import androidx.room.Room
import com.jh.reminder.data.db.ReminderDatabase
import com.jh.reminder.data.preference.AppPreference
import com.jh.reminder.data.repository.LocalRepository
import com.jh.reminder.data.repository.LocalRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalProvidesModule {

    @Provides
    @Singleton
    fun providesReminderDatabase(@ApplicationContext context: Context): ReminderDatabase {
        return Room.databaseBuilder(context, ReminderDatabase::class.java, "ReminderDatabase.db")
            .build()
    }

    @Provides
    @Singleton
    fun providesReminderDAO(reminderDatabase: ReminderDatabase) = reminderDatabase.reminderDao()

    @Provides
    @Singleton
    fun providesPreference(@ApplicationContext context: Context): AppPreference = AppPreference(context)

}

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalBindsModule {

    @Binds
    @Singleton
    abstract fun bindsLocalRepository(localRepositoryImpl: LocalRepositoryImpl): LocalRepository

}