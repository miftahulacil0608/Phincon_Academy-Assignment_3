package com.example.mytraver.data.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ItineraryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addItinerary(itineraryEntity: ItineraryEntity)

    @Query("SELECT * FROM itineraryEntity ORDER BY id DESC")
    fun getListItinerary(): Flow<List<ItineraryEntity>>

    @Query("SELECT * FROM itineraryEntity WHERE :id LIKE id")
    fun getDetailItinerary(id: Int): Flow<ItineraryEntity>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateItinerary(itineraryEntity: ItineraryEntity)

    @Delete
    suspend fun deleteItinerary(itineraryEntity: ItineraryEntity)

}