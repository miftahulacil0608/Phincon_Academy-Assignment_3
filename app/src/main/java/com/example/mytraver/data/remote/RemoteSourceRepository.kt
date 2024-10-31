package com.example.mytraver.data.remote

import com.example.mytraver.data.model.DetailDestinationDto
import com.example.mytraver.data.model.ListRecommendedDto
import com.example.mytraver.data.model.LoginResponseDto

interface RemoteSourceRepository {
    suspend fun login(email:String, password:String):LoginResponseDto

    //TODO DELETE
    suspend fun getListRecommended(page:Int):ListRecommendedDto

    suspend fun getLisRecommendedByType(page:Int?,type:String,query:String?):ListRecommendedDto
    suspend fun getListPopular(page:Int?, query: String?):ListRecommendedDto
    suspend fun getDetailDestination(id:Int):DetailDestinationDto


}