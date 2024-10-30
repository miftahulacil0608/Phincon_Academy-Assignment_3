package com.example.mytraver.domain.repository

import com.example.mytraver.data.local.room.ItineraryEntity
import com.example.mytraver.data.local.room.LocalItineraryRepository
import com.example.mytraver.domain.model.ItineraryData
import com.example.mytraver.domain.repository.contract.ItineraryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ItineraryRepositoryImpl @Inject constructor(private val localItineraryRepository: LocalItineraryRepository) :
    ItineraryRepository {

    override suspend fun addItinerary(itineraryData: ItineraryData): Boolean {
        return try {
            localItineraryRepository.addItinerary(itineraryData.toItineraryEntity())
            true
        } catch (e: Exception) {
            throw IllegalStateException(e.message)
        }
    }

    override suspend fun getListItinerary(): Flow<List<ItineraryData>> {
        return localItineraryRepository.getListItinerary().map {
            it.map { itineraryEntityData ->
                itineraryEntityData.toItineraryData()
            }
        }
    }

    override suspend fun getDetailItinerary(idDestination: Int): Flow<ItineraryData> {
        return localItineraryRepository.getDetailItinerary(idDestination).map {
            it.toItineraryData()
        }
    }

    override suspend fun updateItinerary(itineraryData: ItineraryData): Boolean {
        return try {
            localItineraryRepository.updateItinerary(itineraryData.toItineraryEntity())
            true
        } catch (e: Exception) {
            throw IllegalStateException("Error Unexpected")
        }
    }

    override suspend fun deleteItinerary(itineraryData: ItineraryData) {
        localItineraryRepository.deleteItinerary(itineraryData.toItineraryEntity())
    }

    companion object {
        fun ItineraryData.toItineraryEntity(): ItineraryEntity {
            return ItineraryEntity(
                //TODO yang bisa bikin error
                id = this.id,
                idDestination = this.idDestination,
                activity = this.activity,
                description = this.description,
                duration = this.duration,
                image = this.image,
                location = this.location,
                name = this.name,
                popularity = this.popularity,
                type = this.type,
                notes = this.notes
            )
        }

        fun ItineraryEntity.toItineraryData(): ItineraryData {
            return ItineraryData(
                id = this.id,
                idDestination = this.idDestination,
                activity = this.activity,
                description = this.description,
                duration = this.duration,
                image = this.image,
                location = this.location,
                name = this.name,
                popularity = this.popularity,
                type = this.type,
                notes = this.notes
            )
        }
    }


}