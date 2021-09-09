package com.example.graduateproject.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.graduateproject.data.local.Database
import com.example.graduateproject.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
            @ApplicationContext context: Context
    ):Database
    {
        return Room.databaseBuilder(
                context,
                Database::class.java,
                Constants.DATA_BASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun ProvideDao(database: Database)=database.getDao()

}