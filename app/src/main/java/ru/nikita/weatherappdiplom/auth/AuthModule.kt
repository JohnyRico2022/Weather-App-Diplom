package ru.nikita.weatherappdiplom.auth

import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class AuthModule {

    @Singleton
    @Provides
    fun providefirebaseAuth():FirebaseAuth = FirebaseAuth.getInstance()
}


