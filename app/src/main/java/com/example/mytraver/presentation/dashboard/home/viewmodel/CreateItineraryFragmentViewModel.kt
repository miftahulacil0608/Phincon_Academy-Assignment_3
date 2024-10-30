package com.example.mytraver.presentation.dashboard.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mytraver.domain.model.ItineraryData
import com.example.mytraver.domain.usecase.ItineraryUseCase
import com.example.mytraver.presentation.StateUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateItineraryFragmentViewModel @Inject constructor(private val itineraryUseCase: ItineraryUseCase) :
    ViewModel() {

    suspend fun addItinerary(itineraryData: ItineraryData):Boolean {
        return itineraryUseCase.addItinerary(itineraryData)
    }

    suspend fun updateItinerary(itineraryData: ItineraryData):Boolean{
        return itineraryUseCase.updateItinerary(itineraryData)
    }

}