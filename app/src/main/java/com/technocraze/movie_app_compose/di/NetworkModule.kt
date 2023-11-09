package com.technocraze.movie_app_compose.di

import com.technocraze.movie_app_compose.BuildConfig
import com.technocraze.movie_app_compose.network.ApiService
import com.technocraze.movie_app_compose.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

  @Singleton
  @Provides
  fun getRetrofitInstance(): Retrofit {
    return Retrofit.Builder()
      .baseUrl(BuildConfig.BASE_API_URL)
      .addConverterFactory(GsonConverterFactory.create())
      .build()
  }

  @Provides
  @Singleton
  fun getMovieApiService(retrofit: Retrofit): ApiService{
    return retrofit.create(ApiService::class.java)
  }

}