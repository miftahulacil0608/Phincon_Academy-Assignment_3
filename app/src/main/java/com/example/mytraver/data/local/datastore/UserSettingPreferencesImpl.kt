package com.example.mytraver.data.local.datastore

import com.example.mytraver.data.model.LoginResponseForDataStore
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserSettingPreferencesImpl @Inject constructor(private val dataStore: UserSettingPreferencesDataStore):UserSettingPreferencesRepository {
    override suspend fun addOnBoarding(isPassed: Boolean) {
        dataStore.addOnBoarding(isPassed)
    }

    override suspend fun addTypeReference(typeReference: String) {
        dataStore.addTypeReference(typeReference)
    }


    override suspend fun addUserData(loginResponseData: LoginResponseForDataStore) {
        dataStore.addUserData(loginResponseData)
    }


    override fun getUserSetting(): Flow<UserSetting> {
        return dataStore.getUserSetting()
    }

    override suspend fun clearSession() {
        dataStore.clearSession()
    }
}