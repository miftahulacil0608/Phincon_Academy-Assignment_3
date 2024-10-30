package com.example.mytraver.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Destination(
    val idDestination:Int,
    val nameDestination: String,
    val location: String,
    val ratingBar: Float,
    val description: String,
    val duration:String,
) : Parcelable
