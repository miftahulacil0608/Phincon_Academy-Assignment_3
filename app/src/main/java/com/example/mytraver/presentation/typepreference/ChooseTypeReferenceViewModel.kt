package com.example.mytraver.presentation.typepreference

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.mytraver.domain.model.UserSettingDomain
import com.example.mytraver.domain.usecase.UserSettingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChooseTypeReferenceViewModel @Inject constructor(private val useCaseUserSetting: UserSettingUseCase) :
    ViewModel() {
    private var _typeReference: MutableLiveData<String> = MutableLiveData()
    val typeReference: LiveData<String> = _typeReference

    init {
        getTypeReferenceUser()
    }

    private fun getTypeReferenceUser(){
        viewModelScope.launch {
            val response = useCaseUserSetting.getUserData()
            response.collect{
                _typeReference.value = it.preferences
            }
        }
    }

    suspend fun chooseTypeReference(typeReference: String): Boolean {
        return useCaseUserSetting.chooseTypeReference(typeReference)
    }


}