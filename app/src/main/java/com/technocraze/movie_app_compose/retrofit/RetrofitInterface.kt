package com.technocraze.movie_app_compose.retrofit

import com.technocraze.movie_app_compose.models.MovieResponse
import com.technocraze.movie_app_compose.models.ReviewResponse
import com.technocraze.movie_app_compose.models.VideoResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitInterface {


  @GET("/3/movie/{category}")
  suspend fun getMovie(
    @Path("category") category: String,
    @Query("api_key") apiKey: String = "c4fd0359f29736975ba764defb5f2878",
    @Query("language") language: String = "en-US",
    @Query("page") page: Int
  ): MovieResponse

  @GET("/3/movie/{movie_id}/videos")
  suspend fun getVideos(
    @Path("movie_id") movieId: Int,
    @Query("api_key") apiKey: String = "c4fd0359f29736975ba764defb5f2878",
    @Query("language") language: String = "en-US",
    ): VideoResponse

  //https://api.themoviedb.org/3/movie/{movie_id}/reviews

  @GET("/3/movie/{movie_id}/reviews")
  suspend fun getReviews(
    @Path("movie_id") movieId: Int,
    @Query("api_key") apiKey: String = "c4fd0359f29736975ba764defb5f2878",
    @Query("language") language: String = "en-US",
  ): ReviewResponse
}