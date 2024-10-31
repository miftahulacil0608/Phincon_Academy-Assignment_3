package com.example.mytraver.data.remote.network

import com.example.mytraver.data.model.DetailDestinationDto
import com.example.mytraver.data.model.ListRecommendedDto
import com.example.mytraver.data.model.LoginInput
import com.example.mytraver.data.model.LoginResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @POST("/login")
    suspend fun login(
        @Body data: LoginInput
    ): LoginResponseDto

    //TODO DELETE
    @GET("travel")
    suspend fun getListDataRecommended(@Query("page") page: Int): ListRecommendedDto

    @GET("travel")
    suspend fun getListTravel(
        @Query("page") page: Int? = null,
        @Query("per_page") perPage: Int? = null,
        @Query("type") type: String? = null,
        @Query("name") name: String? = null,
    ): ListRecommendedDto

    @GET("travel/{id}")
    suspend fun getDetailTravel(@Path("id") id: Int):DetailDestinationDto

}