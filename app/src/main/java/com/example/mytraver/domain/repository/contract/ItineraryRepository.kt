package com.example.mytraver.domain.repository.contract

import com.example.mytraver.data.local.room.ItineraryEntity
import com.example.mytraver.domain.model.ItineraryData
import kotlinx.coroutines.flow.Flow

interface ItineraryRepository {
    suspend fun addItinerary(itineraryData: ItineraryData):Boolean
    suspend fun getListItinerary(): Flow<List<ItineraryData>>
    suspend fun getDetailItinerary(idDestination:Int):Flow<ItineraryData>
    suspend fun updateItinerary(itineraryData: ItineraryData):Boolean
    suspend fun deleteItinerary(itineraryData: ItineraryData)
}