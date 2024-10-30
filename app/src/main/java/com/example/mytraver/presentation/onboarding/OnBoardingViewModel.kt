package com.example.mytraver.presentation.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mytraver.domain.usecase.UserSettingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(private val userSettingUseCase: UserSettingUseCase):ViewModel() {
    fun addOnBoardingPassed(isPassed:Boolean){
        viewModelScope.launch {
            userSettingUseCase.addOnBoardingPassed(isPassed)
        }
    }
}