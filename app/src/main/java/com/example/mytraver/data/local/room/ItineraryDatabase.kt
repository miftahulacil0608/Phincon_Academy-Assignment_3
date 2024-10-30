package com.example.mytraver.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ItineraryEntity::class], version = 1, exportSchema = false)
abstract class ItineraryDatabase : RoomDatabase() {
    abstract fun itineraryDao(): ItineraryDao
}