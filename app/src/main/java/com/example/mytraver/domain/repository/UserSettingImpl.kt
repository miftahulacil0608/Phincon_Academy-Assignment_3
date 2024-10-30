package com.example.mytraver.domain.repository

import com.example.mytraver.data.local.datastore.UserSetting
import com.example.mytraver.data.local.datastore.UserSettingPreferencesRepository
import com.example.mytraver.domain.model.UserSettingDomain
import com.example.mytraver.domain.repository.contract.UserSettingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserSettingImpl @Inject constructor(private val userSettingPreferencesRepository: UserSettingPreferencesRepository) :
    UserSettingRepository {
    override suspend fun addOnboardingPassed(isPassed: Boolean) {
        userSettingPreferencesRepository.addOnBoarding(isPassed)
    }

    override suspend fun addTypeReference(typeReference: String): Boolean {
        return try {
            userSettingPreferencesRepository.addTypeReference(typeReference)
            true
        }catch (e:Exception){
            throw IllegalAccessException(e.message)
        }
    }

    override fun getUserData(): Flow<UserSettingDomain> {
        return userSettingPreferencesRepository.getUserSetting().map {
            it.toUSerSettingDomain()
        }
    }

    override suspend fun clearSession() {
        userSettingPreferencesRepository.clearSession()
    }


    companion object {
        fun UserSetting.toUSerSettingDomain(): UserSettingDomain {
            return UserSettingDomain(
                token = this.token,
                onBoardingPassed = this.onBoardingPassed,
                preferences = this.preferences,
                username = this.username,
                email = this.email,
                phone = this.phone,
                avatar = this.avatar,
                password = this.password
            )
        }
    }

}