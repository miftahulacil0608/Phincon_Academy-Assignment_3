package com.example.mytraver.presentation.dashboard.home.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.mytraver.domain.model.DataItem
import com.example.mytraver.domain.model.ListRecommendedResponse
import com.example.mytraver.domain.usecase.DestinationUseCase
import com.example.mytraver.domain.usecase.UserSettingUseCase
import com.example.mytraver.presentation.StateUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
    private val userSettingUseCase: UserSettingUseCase,
    private val destinationUseCase: DestinationUseCase
) : ViewModel() {


    private val _recommendedDestination =
        MutableStateFlow<StateUser<List<DataItem>>>(StateUser.Loading)
    val recommendedDestination = _recommendedDestination.asStateFlow()

    private val _popularDestination =
        MutableStateFlow<StateUser<List<DataItem>>>(StateUser.Loading)
    val popularDestination = _popularDestination.asStateFlow()

    val userData = liveData {
        userSettingUseCase.getUserData().collect {
            emit(it)
        }
    }

    init {
        getListRecommendedDestination()
        getListPopularDestination()
    }

    private fun getListRecommendedDestination() {
        viewModelScope.launch {
            _recommendedDestination.value = StateUser.Loading
            try {
                val type = userSettingUseCase.getUserData().first().preferences
                val response = destinationUseCase.getListRecommendedByType(null, type, query = null)
                _recommendedDestination.value = StateUser.Success(response.dataItem)
            } catch (e: Exception) {
                _recommendedDestination.value = StateUser.Error(e.message.toString())
            }
        }
    }

    private fun getListPopularDestination() {
        viewModelScope.launch {
            _popularDestination.value = StateUser.Loading
            try {
                val responsePopular = destinationUseCase.getListPopular(null,null)
                _popularDestination.value = StateUser.Success(responsePopular.dataItem)
            } catch (e: Exception) {
                _popularDestination.value = StateUser.Error(e.message.toString())
            }
        }
    }


}