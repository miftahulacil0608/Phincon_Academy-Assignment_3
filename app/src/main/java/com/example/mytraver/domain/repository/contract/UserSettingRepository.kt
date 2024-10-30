package com.example.mytraver.domain.repository.contract

import com.example.mytraver.domain.model.UserSettingDomain
import kotlinx.coroutines.flow.Flow

interface UserSettingRepository {
    suspend fun addOnboardingPassed(isPassed:Boolean)
    suspend fun addTypeReference(typeReference:String):Boolean
    fun getUserData(): Flow<UserSettingDomain>
    suspend fun clearSession()
}