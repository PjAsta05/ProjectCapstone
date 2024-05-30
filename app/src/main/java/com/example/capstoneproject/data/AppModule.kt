package com.example.capstoneproject.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.capstoneproject.data.pref.UserPreference
import com.example.capstoneproject.data.pref.dataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStore
    }

    @Provides
    @Singleton
    fun provideUserPreferences(dataStore: DataStore<Preferences>): UserPreference {
        return UserPreference.getInstance(dataStore)
    }

    @Provides
    @Singleton
    fun provideRepository(
        userPreference: UserPreference
    ): Repository {
        return Repository(userPreference)
    }
}