package com.example.mytraver.presentation.dashboard.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mytraver.domain.model.DetailDestinationResponse
import com.example.mytraver.domain.usecase.DestinationUseCase
import com.example.mytraver.presentation.StateUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailDestinationFragmentViewModel @Inject constructor(private val useCaseDestination: DestinationUseCase):
    ViewModel(){
    private val _dataDetailDestination = MutableStateFlow<StateUser<DetailDestinationResponse>>(StateUser.Loading)
    val dataDetailDestination = _dataDetailDestination.asStateFlow()

    fun getDetailDestination(id:Int){
        viewModelScope.launch {
            _dataDetailDestination.value = StateUser.Loading
            try {
                val responseDetail = useCaseDestination.getDetailDestination(id)
                _dataDetailDestination.value = StateUser.Success(responseDetail)
            }catch (e:Exception){
                _dataDetailDestination.value = StateUser.Error(e.message.toString())
            }
        }
    }
}