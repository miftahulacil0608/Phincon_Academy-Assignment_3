package com.example.mytraver.data.local.datastore

import com.example.mytraver.data.model.LoginResponseForDataStore
import kotlinx.coroutines.flow.Flow

interface UserSettingPreferencesRepository {
    suspend fun addOnBoarding(isPassed:Boolean)
    suspend fun addTypeReference(typeReference:String)
    suspend fun addUserData(loginResponseData:LoginResponseForDataStore)
    fun getUserSetting(): Flow<UserSetting>
    suspend fun clearSession()
}