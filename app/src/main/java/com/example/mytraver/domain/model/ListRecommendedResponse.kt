package com.example.mytraver.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class ListRecommendedResponse(
    val dataItem: List<DataItem>,
    val message: String,
    val page: Int,
    val perPage: Int,
    val success: Boolean,
    val totalData: Int,
    val totalPage: Int
)

@Parcelize
class DataItem(
    val activity: String,
    val description: String,
    val duration: String,
    val id: Int,
    val image: String,
    val location: String,
    val name: String,
    val popularity: String,
    val type: String
):Parcelable
