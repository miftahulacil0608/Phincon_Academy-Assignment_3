package com.example.mytraver.domain.repository

import com.example.mytraver.data.local.datastore.UserSettingPreferencesRepository
import com.example.mytraver.data.model.DetailDestinationDto
import com.example.mytraver.data.model.ListRecommendedDto
import com.example.mytraver.data.model.LoginResponseDto
import com.example.mytraver.data.model.LoginResponseForDataStore
import com.example.mytraver.data.remote.RemoteSourceRepository
import com.example.mytraver.domain.model.DataItem
import com.example.mytraver.domain.model.DetailDestinationResponse
import com.example.mytraver.domain.model.ListRecommendedResponse
import com.example.mytraver.domain.model.LoginResponse
import com.example.mytraver.domain.repository.contract.NetworkRepository
import javax.inject.Inject

class NetworkRepositoryImpl @Inject constructor(
    private val remoteSourceRepository: RemoteSourceRepository,
    private val userSettingPreferencesRepository: UserSettingPreferencesRepository
) :
    NetworkRepository {
    override suspend fun login(email: String, password: String): String {
        return try {
            val loginSuccess = remoteSourceRepository.login(email, password)
            loginSuccess.data.toLoginResponse().let {
                userSettingPreferencesRepository.addUserData(
                    LoginResponseForDataStore(
                        it.username,
                        it.email,
                        it.phone,
                        it.avatar,
                        it.token,
                        password,
                    )
                )
            }
            loginSuccess.message
        } catch (e: Exception) {
            throw IllegalStateException("${e.message}")
        }

    }

    //nanti bisa dihapus ko
    override suspend fun getListRecommended(page: Int): ListRecommendedResponse {
        return remoteSourceRepository.getListRecommended(page).toListRecommendedResponse()
    }

    override suspend fun getListRecommendedByType(
        page: Int?,
        type: String,
        query:String?
    ): ListRecommendedResponse {
        return remoteSourceRepository.getLisRecommendedByType(page = page, type = type, query = query)
            .toListRecommendedResponse()
    }

    override suspend fun getListPopular(page: Int?, query: String?): ListRecommendedResponse {
        return remoteSourceRepository.getListPopular(page = page, query = query).toListRecommendedResponse()
    }

    override suspend fun getDetailDestination(id: Int): DetailDestinationResponse {
        return remoteSourceRepository.getDetailDestination(id).data.toDetailDestinationResponse()
    }




    companion object {

        fun DetailDestinationDto.Data.toDetailDestinationResponse(): DetailDestinationResponse {
            return DetailDestinationResponse(
                this.activity,
                this.description,
                this.duration,
                this.id,
                this.image,
                this.location,
                this.name,
                this.popularity,
                this.type
            )
        }

        fun LoginResponseDto.Data.toLoginResponse(): LoginResponse {
            return LoginResponse(
                this.firstName + " " + this.lastName,
                this.email,
                this.phone,
                this.avatar,
                this.token
            )
        }

        fun ListRecommendedDto.toListRecommendedResponse(): ListRecommendedResponse {
            return ListRecommendedResponse(
                dataItem = this.data.map { it.toDataItem() },
                message = this.message,
                page = this.page,
                perPage = this.perPage,
                totalData = this.todalData,
                totalPage = this.totalPage,
                success = this.success

            )
        }

        private fun ListRecommendedDto.Data.toDataItem(): DataItem {
            return DataItem(
                this.activity,
                this.description,
                this.duration,
                this.id,
                this.image,
                this.location,
                this.name,
                this.popularity,
                this.type
            )
        }

    }
}