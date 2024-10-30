package com.example.mytraver.presentation.dashboard.itinerary.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mytraver.domain.model.ItineraryData
import com.example.mytraver.domain.usecase.ItineraryUseCase
import com.example.mytraver.presentation.StateUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItineraryFragmentViewModel @Inject constructor(private val itineraryUseCase: ItineraryUseCase) :
    ViewModel() {
    private val _listItinerary = MutableStateFlow<StateUser<List<ItineraryData>>>(StateUser.Loading)
    val listItinerary = _listItinerary.asStateFlow()

    fun getListItinerary() {
        viewModelScope.launch {
            _listItinerary.value = StateUser.Loading
            try {
                val responseGetList = itineraryUseCase.getListItinerary()
                responseGetList.collect {
                    _listItinerary.value = StateUser.Success(it)
                }
            } catch (e: Exception) {
                _listItinerary.value = StateUser.Error("Error Unexpected")
            }
        }
    }


}