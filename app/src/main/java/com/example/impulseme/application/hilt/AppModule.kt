package com.example.impulseme.application.hilt

import com.example.impulseme.repository.FakeReminderRepository
import com.example.impulseme.repository.ReminderRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideReminderRepository(): ReminderRepository {
        return FakeReminderRepository()
    }
}