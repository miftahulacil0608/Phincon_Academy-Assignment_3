package com.example.mytraver.domain.usecase

import com.example.mytraver.domain.model.DetailDestinationResponse
import com.example.mytraver.domain.model.ListRecommendedResponse
import com.example.mytraver.domain.repository.contract.NetworkRepository
import javax.inject.Inject

class DestinationUseCase @Inject constructor(private val networkRepository: NetworkRepository){
    suspend fun getListRecommended(page:Int):ListRecommendedResponse{
        return networkRepository.getListRecommended(page)
    }

    suspend fun getListRecommendedByType(page:Int?, type:String, query:String?):ListRecommendedResponse{
        return networkRepository.getListRecommendedByType(page =page, type =  type, query = query)
    }
    suspend fun getListPopular(page:Int?, query:String?):ListRecommendedResponse{
        return networkRepository.getListPopular(page=page, query= query)
    }

    suspend fun getDetailDestination(id:Int):DetailDestinationResponse{
        return networkRepository.getDetailDestination(id)
    }
}