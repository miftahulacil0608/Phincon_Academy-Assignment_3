package com.example.mytraver.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ItineraryData(
    val id: Int?,
    val idDestination: Int,
    val activity: String,
    val description: String,
    val duration: String,
    val image: String,
    val location: String,
    val name: String,
    val popularity: String,
    val type: String,
    val notes:String,
):Parcelable
