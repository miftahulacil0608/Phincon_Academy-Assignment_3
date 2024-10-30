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
class DetailItineraryFragmentViewModel @Inject constructor(private val itineraryUseCase: ItineraryUseCase) : ViewModel() {
    private val _detailItinerary = MutableStateFlow<StateUser<ItineraryData>>(StateUser.Loading)
    val listItinerary = _detailItinerary.asStateFlow()

    fun getDetailItinerary(idItinerary:Int) {
        viewModelScope.launch {
            _detailItinerary.value = StateUser.Loading
            try {
                val responseGetList = itineraryUseCase.getDetailItinerary(idItinerary)
                responseGetList.collect {
                    _detailItinerary.value = StateUser.Success(it)
                }
            } catch (e: Exception) {
                _detailItinerary.value = StateUser.Error("Error Unexpected")
            }
        }
    }


    fun deleteDataItinerary(itineraryData: ItineraryData){
        viewModelScope.launch {
            itineraryUseCase.deleteItinerary(itineraryData)
        }
    }
}