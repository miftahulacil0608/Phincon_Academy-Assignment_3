package com.example.mytraver.presentation.dashboard.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mytraver.domain.model.PersonalInformation
import com.example.mytraver.domain.usecase.UserSettingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val userSettingUseCase: UserSettingUseCase) :
    ViewModel() {
    private val _userPersonalInformation = MutableLiveData<PersonalInformation>()
    val userPersonalInformation: LiveData<PersonalInformation> = _userPersonalInformation

    fun getPersonalInformation() {
        viewModelScope.launch {
            userSettingUseCase.getUserData().collect {
                _userPersonalInformation.value = PersonalInformation(it.avatar, it.username, it.email, it.phone, it.password)
            }
        }
    }

    fun clearLogout(){
        viewModelScope.launch {
            userSettingUseCase.clearSession()
        }
    }
}