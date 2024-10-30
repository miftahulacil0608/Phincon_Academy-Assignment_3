package com.example.mytraver.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.example.mytraver.data.local.datastore.UserSettingPreferencesDataStore
import com.example.mytraver.data.local.datastore.UserSettingPreferencesImpl
import com.example.mytraver.data.local.datastore.UserSettingPreferencesRepository
import com.example.mytraver.data.local.room.ItineraryDatabase
import com.example.mytraver.data.local.room.LocalItineraryRepository
import com.example.mytraver.data.local.room.LocalItineraryRepositoryImpl
import com.example.mytraver.data.remote.RemoteSourceRepository
import com.example.mytraver.data.remote.RemoteSourceRepositoryImpl
import com.example.mytraver.data.remote.network.ApiService
import com.example.mytraver.domain.repository.ItineraryRepositoryImpl
import com.example.mytraver.domain.repository.NetworkRepositoryImpl
import com.example.mytraver.domain.repository.UserSettingImpl
import com.example.mytraver.domain.repository.contract.ItineraryRepository
import com.example.mytraver.domain.repository.contract.NetworkRepository
import com.example.mytraver.domain.repository.contract.UserSettingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {

    private val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = "user_data_store")

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.datastore
    }

    @Provides
    @Singleton
    fun provideUserSettingPreferencesDataStore(@ApplicationContext context: Context): UserSettingPreferencesDataStore {
        return UserSettingPreferencesDataStore(context.datastore)
    }

    @Provides
    @Singleton
    fun provideUserSettingPreferencesDataStoreRepository(userSettingPreferences: UserSettingPreferencesDataStore): UserSettingPreferencesRepository {
        return UserSettingPreferencesImpl(userSettingPreferences)
    }

    @Provides
    @Singleton
    fun provideInterceptor(userSettingPreferences: UserSettingPreferencesDataStore): Interceptor {
        return Interceptor {
            val chain = it.request()
            val requestBuilder = chain.newBuilder()
            val token = runBlocking {
                userSettingPreferences.getToken().first()
            }
            token?.let {
                requestBuilder.addHeader("Authorization", "Bearer $it")
            }

            it.proceed(requestBuilder.build())
        }
    }

    @Provides
    @Singleton
    fun provideOkhttpInterceptor(interceptor: Interceptor): OkHttpClient {
        val httpLoggingBody = HttpLoggingInterceptor.Level.BODY
        val httpInterceptor = HttpLoggingInterceptor()
        httpInterceptor.level = httpLoggingBody

        return OkHttpClient.Builder()
            .addInterceptor(httpInterceptor)
            .addInterceptor(interceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(okHttpClient: OkHttpClient): ApiService {
        return Retrofit.Builder()
            .baseUrl("http://167.99.74.195:8090")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun providesRoomDatabase(@ApplicationContext context: Context): ItineraryDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            ItineraryDatabase::class.java,
            name = "db_itinerary"
        )
            .build()

    }

    @Provides
    @Singleton
    fun providesLocalItineraryRepository(database: ItineraryDatabase): LocalItineraryRepository {
        return LocalItineraryRepositoryImpl(database)
    }

    @Provides
    @Singleton
    fun providesItineraryRepository(localItineraryRepository: LocalItineraryRepository):ItineraryRepository{
        return ItineraryRepositoryImpl(localItineraryRepository)
    }

    @Provides
    @Singleton
    fun provideUserSettingRepository(userSettingPreferencesRepository: UserSettingPreferencesRepository): UserSettingRepository {
        return UserSettingImpl(userSettingPreferencesRepository)
    }

    @Provides
    @Singleton
    fun provideRemoteSourceRepository(apiService: ApiService): RemoteSourceRepository {
        return RemoteSourceRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideNetworkRepository(
        remoteSourceRepository: RemoteSourceRepository,
        userSettingPreferencesRepository: UserSettingPreferencesRepository
    ): NetworkRepository {
        return NetworkRepositoryImpl(remoteSourceRepository, userSettingPreferencesRepository)
    }

}