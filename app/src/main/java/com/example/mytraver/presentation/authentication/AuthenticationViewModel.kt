package com.example.mytraver.presentation.authentication

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.mytraver.domain.usecase.UserSettingUseCase
import com.example.mytraver.presentation.StateUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(private val userSettingUseCase: UserSettingUseCase) :
    ViewModel() {

    private val _preferences: MutableLiveData<String> = MutableLiveData()
    val preferences: LiveData<String> = _preferences

    init {
        userSettingData()
    }

    fun login(email: String, password: String) = flow<StateUser<String>> {
        emit(StateUser.Loading)
        try {
            val data = userSettingUseCase.login(email, password)
            emit(StateUser.Success(data))
        } catch (e: Exception) {
            emit(StateUser.Error(e.message.toString()))
        }
    }

    private fun userSettingData(){
        viewModelScope.launch {
            userSettingUseCase.getUserData().collect{
                _preferences.value = it.preferences
                Log.d("Value Preferences", "userSettingData: ${it.preferences}")
            }
        }
    }
}