package com.example.capstoneproject.data.pref

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

class UserPreference private constructor(private val dataStore: DataStore<Preferences>) {
    suspend fun saveSession(user: UserModel) {
        dataStore.edit {
            it[ID] = user.id
            it[NAME] = user.name
            it[EMAIL] = user.email
            it[TOKEN] = user.token
            it[IS_LOGIN] = true
        }
    }

    suspend fun updateSession(user: UserModel) {
        val currentUser = getSession().first()

        val updatedUser = currentUser.copy(
            id = currentUser.id,
            name = user.name.ifBlank { currentUser.name },
            email = user.email.ifBlank { currentUser.email },
            photo = user.photo.ifBlank { currentUser.photo },
            token = currentUser.token,
            isLogin = true
        )
        
        saveSession(updatedUser)
    }

    fun getSession(): Flow<UserModel> {
        return dataStore.data.map {
            UserModel(
                it[ID] ?: 0,
                it[NAME] ?: "",
                it[EMAIL] ?: "",
                it[PHOTO] ?: "",
                it[TOKEN] ?: "",
                it[IS_LOGIN] ?: false
            )
        }
    }

    suspend fun deleteSession() {
        dataStore.edit {
            it.clear()
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreference? = null

        private val ID = intPreferencesKey("id")
        private val NAME = stringPreferencesKey("name")
        private val EMAIL = stringPreferencesKey("email")
        private val PHOTO = stringPreferencesKey("photo")
        private val TOKEN = stringPreferencesKey("token")
        private val IS_LOGIN = booleanPreferencesKey("isLogin")

        fun getInstance(dataStore: DataStore<Preferences>): UserPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}