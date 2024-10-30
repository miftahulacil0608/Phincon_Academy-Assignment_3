package com.example.mytraver.data.remote

import com.example.mytraver.data.model.DetailDestinationDto
import com.example.mytraver.data.model.ListRecommendedDto
import com.example.mytraver.data.model.LoginInput
import com.example.mytraver.data.model.LoginResponseDto
import com.example.mytraver.data.remote.network.ApiService

class RemoteSourceRepositoryImpl(private val apiService: ApiService):RemoteSourceRepository {
    override suspend fun login(email: String, password: String): LoginResponseDto {
        return apiService.login(LoginInput(email,password))
    }

    //nanti dirubah menjadi full list oke
    override suspend fun getListRecommended(page: Int): ListRecommendedDto {
        return apiService.getListDataRecommended(page)
    }

    override suspend fun getLisRecommendedByType(page:Int?,type: String,query:String?): ListRecommendedDto {
        return apiService.getListTravel(page = page, type = type, name = query)
    }

    override suspend fun getListPopular(page:Int?, query: String?): ListRecommendedDto {
        return apiService.getListTravel(page = page, name = query)
    }

    override suspend fun getDetailDestination(id: Int): DetailDestinationDto {
        return apiService.getDetailTravel(id)
    }


}