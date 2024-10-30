package com.example.mytraver.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.mytraver.data.model.LoginResponseForDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserSettingPreferencesDataStore(private val dataStore: DataStore<Preferences>) {
    suspend fun addOnBoarding(isPassed: Boolean) {
        dataStore.edit {
            it[KEY_ON_BOARDING] = isPassed
        }
    }

    suspend fun addTypeReference(typeReference: String) {
        dataStore.edit {
            it[KEY_PREFERENCES_ACTIVITY] = typeReference
        }
    }

    fun getToken(): Flow<String?> {
        return dataStore.data.map {
            it[KEY_TOKEN] ?: ""
        }
    }

    suspend fun addUserData(loginResponseData: LoginResponseForDataStore) {
        dataStore.edit {
            it[KEY_TOKEN] = loginResponseData.token
            it[KEY_USERNAME] = loginResponseData.username
            it[KEY_EMAIL] = loginResponseData.email
            it[KEY_PHONE] = loginResponseData.phone
            it[KEY_AVATAR] = loginResponseData.avatar
            it[KEY_PASSWORD] = loginResponseData.password
        }
    }

    fun getUserSetting(): Flow<UserSetting> {
        return dataStore.data.map {
            UserSetting(
                onBoardingPassed = it[KEY_ON_BOARDING] ?: false,
                token = it[KEY_TOKEN] ?: "",
                preferences = it[KEY_PREFERENCES_ACTIVITY] ?: "",
                username = it[KEY_USERNAME] ?: "",
                email = it[KEY_EMAIL] ?: "",
                phone = it[KEY_PHONE] ?: "",
                avatar = it[KEY_AVATAR] ?: "",
                password = it[KEY_PASSWORD] ?: "",

                )
        }
    }

    suspend fun clearSession() {
        dataStore.edit {
            it.remove(KEY_TOKEN)
            it.remove(KEY_USERNAME)
            it.remove(KEY_EMAIL)
            it.remove(KEY_PHONE)
            it.remove(KEY_AVATAR)
            it.remove(KEY_PASSWORD)
        }
    }

    companion object {
        private val KEY_ON_BOARDING = booleanPreferencesKey("KEY ON BOARDING")
        private val KEY_TOKEN = stringPreferencesKey("KEY ON LOGIN")
        private val KEY_PREFERENCES_ACTIVITY = stringPreferencesKey("KEY PREFERENCES ACTIVITY")
        private val KEY_USERNAME = stringPreferencesKey("KEY USERNAME")
        private val KEY_EMAIL = stringPreferencesKey("KEY EMAIL")
        private val KEY_PHONE = stringPreferencesKey("KEY PHONE")
        private val KEY_AVATAR = stringPreferencesKey("KEY AVATAR")
        private val KEY_PASSWORD = stringPreferencesKey("KEY PASSWORD")
    }
}