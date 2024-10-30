package com.example.mytraver.data.local.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class ItineraryEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    val id: Int? = 0,
    @ColumnInfo("idDestination")
    val idDestination: Int,
    @ColumnInfo("activity")
    val activity: String,
    @ColumnInfo("description")
    val description: String,
    @ColumnInfo("duration")
    val duration: String,
    @ColumnInfo("image")
    val image: String,
    @ColumnInfo("location")
    val location: String,
    @ColumnInfo("name")
    val name: String,
    @ColumnInfo("popularity")
    val popularity: String,
    @ColumnInfo("type")
    val type: String,
    @ColumnInfo("notes")
    val notes: String
    )
