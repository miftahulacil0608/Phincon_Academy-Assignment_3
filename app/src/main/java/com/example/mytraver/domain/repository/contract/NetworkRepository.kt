package com.example.mytraver.domain.repository.contract

import com.example.mytraver.data.model.ListRecommendedDto
import com.example.mytraver.domain.model.DetailDestinationResponse
import com.example.mytraver.domain.model.ListRecommendedResponse
import com.example.mytraver.domain.model.LoginResponse

interface NetworkRepository {
    suspend fun login(email:String, password:String):String
    suspend fun getListRecommended(page:Int):ListRecommendedResponse
    suspend fun getListRecommendedByType(page:Int?,type:String, query:String?):ListRecommendedResponse
    suspend fun getListPopular(page:Int?, query: String?):ListRecommendedResponse
    suspend fun getDetailDestination(id:Int):DetailDestinationResponse
}