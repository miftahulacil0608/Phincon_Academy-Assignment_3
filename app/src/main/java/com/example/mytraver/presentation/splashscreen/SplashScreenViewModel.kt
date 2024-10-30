package com.example.mytraver.presentation.splashscreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.mytraver.domain.usecase.UserSettingUseCase
import com.example.mytraver.domain.model.UserSettingDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(private val userSettingUseCase: UserSettingUseCase) :
    ViewModel() {
    val userSettingData = liveData<UserSettingDomain> {
        userSettingUseCase.getUserData().collect{
            emit(it)
        }
    }
}