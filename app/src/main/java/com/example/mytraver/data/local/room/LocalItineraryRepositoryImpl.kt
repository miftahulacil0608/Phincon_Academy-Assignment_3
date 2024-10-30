package com.example.mytraver.data.local.room

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalItineraryRepositoryImpl @Inject constructor (private val database:ItineraryDatabase):LocalItineraryRepository {
    override suspend fun addItinerary(itineraryEntity: ItineraryEntity) {
        database.itineraryDao().addItinerary(itineraryEntity)
    }

    override fun getListItinerary(): Flow<List<ItineraryEntity>> {
        return database.itineraryDao().getListItinerary()
    }

    override fun getDetailItinerary(idDestination:Int): Flow<ItineraryEntity> {
        return database.itineraryDao().getDetailItinerary(idDestination)
    }

    override suspend fun updateItinerary(itineraryEntity: ItineraryEntity) {
        database.itineraryDao().updateItinerary(itineraryEntity)
    }

    override suspend fun deleteItinerary(itineraryEntity: ItineraryEntity) {
        database.itineraryDao().deleteItinerary(itineraryEntity)
    }
}