package com.example.graduateproject.di

import com.google.firebase.auth.FirebaseAuth
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(ActivityRetainedComponent::class)
object FirenaseModule {

    @Provides
    @ActivityRetainedScoped
    fun provideFirebaseAuth():FirebaseAuth
    {
         return FirebaseAuth.getInstance()
    }
}