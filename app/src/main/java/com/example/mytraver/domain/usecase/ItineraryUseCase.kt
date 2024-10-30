package com.example.mytraver.domain.usecase

import com.example.mytraver.domain.model.ItineraryData
import com.example.mytraver.domain.repository.contract.ItineraryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ItineraryUseCase @Inject constructor(private val itineraryRepository: ItineraryRepository) {
    suspend fun addItinerary(itineraryData:ItineraryData):Boolean{
        return itineraryRepository.addItinerary(itineraryData)
    }
    suspend fun getListItinerary(): Flow<List<ItineraryData>>{
        return itineraryRepository.getListItinerary()
    }

    suspend fun getDetailItinerary(idDestination:Int):Flow<ItineraryData>{
        return itineraryRepository.getDetailItinerary(idDestination)
    }

    suspend fun updateItinerary(itineraryData: ItineraryData):Boolean{
        return itineraryRepository.updateItinerary(itineraryData)
    }

    suspend fun deleteItinerary(itineraryData: ItineraryData){
        itineraryRepository.deleteItinerary(itineraryData)
    }
}