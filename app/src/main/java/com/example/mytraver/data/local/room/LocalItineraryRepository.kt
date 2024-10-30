package com.example.mytraver.data.local.room

import kotlinx.coroutines.flow.Flow

interface LocalItineraryRepository {
    suspend fun addItinerary(itineraryEntity: ItineraryEntity)
    fun getListItinerary(): Flow<List<ItineraryEntity>>
    fun getDetailItinerary(idDestination:Int): Flow<ItineraryEntity>
    suspend fun updateItinerary(itineraryEntity: ItineraryEntity)
    suspend fun deleteItinerary(itineraryEntity: ItineraryEntity)
}