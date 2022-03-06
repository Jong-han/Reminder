package com.jh.reminder.di

import com.jh.reminder.usecase.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class UseCaseModule {

    @Binds
    abstract fun bindsInsertUseCase(insertUseCaseImpl: InsertUseCaseImpl): InsertUseCase

    @Binds
    abstract fun bindsGetUseCase(getUseCaseImpl: GetUseCaseImpl): GetUseCase

    @Binds
    abstract fun bindsUpdateUseCase(updateUseCaseImpl: UpdateUseCaseImpl): UpdateUseCase

}