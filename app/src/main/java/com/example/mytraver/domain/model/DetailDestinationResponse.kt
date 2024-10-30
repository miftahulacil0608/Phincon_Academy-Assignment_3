package com.example.mytraver.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailDestinationResponse(
    val activity: String,
    val description: String,
    val duration: String,
    val id: Int,
    val image: String,
    val location: String,
    val name: String,
    val popularity: String,
    val type: String
) : Parcelable
