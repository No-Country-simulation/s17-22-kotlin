package com.nocountry.listmate.data.local

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

private const val USER_PREFERENCES = "user_pref"

class SettingsDataStore(private val context: Context) {

    companion object{
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = USER_PREFERENCES)
        private val USER_ID_KEY = stringPreferencesKey("userId")
        private val NAME_KEY = stringPreferencesKey("name")
        private val LAST_NAME_KEY = stringPreferencesKey("lastName")
    }

    val userFlow: Flow<String> = context.dataStore.data
        .catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            preferences[USER_ID_KEY] ?: ""
        }

    val getUserId : Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[USER_ID_KEY] ?: ""
        }

    val getName : Flow<String> = context.dataStore.data
        .catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            preferences[NAME_KEY] ?: ""
        }

    val getLastName : Flow<String> = context.dataStore.data
        .catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            preferences[LAST_NAME_KEY] ?: ""
        }

    suspend fun saveUserId(userId: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_ID_KEY] = userId
        }
    }

    suspend fun saveName(name: String) {
        context.dataStore.edit { preferences ->
            preferences[NAME_KEY] = name
        }
    }

    suspend fun saveLastName(lastName: String) {
        context.dataStore.edit { preferences ->
            preferences[LAST_NAME_KEY] = lastName
        }
    }

    suspend fun clearUserPreferences(context: Context) {
        context.dataStore.edit { preferences ->
            preferences.remove(USER_ID_KEY)
            preferences.remove(NAME_KEY)
            preferences.remove(LAST_NAME_KEY)
        }
    }
}
