package com.example.mytraver.domain.usecase

import com.example.mytraver.domain.model.UserSettingDomain
import com.example.mytraver.domain.repository.contract.NetworkRepository
import com.example.mytraver.domain.repository.contract.UserSettingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserSettingUseCase @Inject constructor(private val userSettingRepository: UserSettingRepository, private val networkRepository: NetworkRepository) {
    suspend fun addOnBoardingPassed(isPassed:Boolean){
        userSettingRepository.addOnboardingPassed(isPassed)
    }

    suspend fun chooseTypeReference(typeReference:String):Boolean{
        return userSettingRepository.addTypeReference(typeReference)
    }

    fun getUserData(): Flow<UserSettingDomain>{
        return userSettingRepository.getUserData()
    }

    //TODO nanti pindahin ke network usecase untuk login aja gpp
    suspend fun login(email:String, password:String):String{
        return networkRepository.login(email,password)
    }

    suspend fun clearSession(){
        userSettingRepository.clearSession()
    }
}