package com.technocraze.movie_app_compose.retrofit

import com.technocraze.movie_app_kotlin.network.ResultCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {

  val baseurl = "https://api.themoviedb.org/3/"

  private lateinit var retrofit: Retrofit



  fun getInstance ():Retrofit {
    if (::retrofit.isInitialized) {
      return retrofit
    } else {
      val b = OkHttpClient.Builder()
      val j = HttpLoggingInterceptor()
      j.setLevel(HttpLoggingInterceptor.Level.BODY)
      val httpClient: OkHttpClient = b.addInterceptor(j).build()
      retrofit = Retrofit.Builder()
        .baseUrl(baseurl)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(ResultCallAdapterFactory())
        .client(httpClient)
        .build()
      return retrofit
    }
  }

}