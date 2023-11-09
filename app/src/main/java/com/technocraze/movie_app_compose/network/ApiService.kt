package com.technocraze.movie_app_compose.network

import com.technocraze.movie_app_compose.BuildConfig
import com.technocraze.movie_app_compose.models.MovieResponse
import com.technocraze.movie_app_compose.models.ReviewResponse
import com.technocraze.movie_app_compose.models.VideoResponse
import com.technocraze.movie_app_compose.network.MovieApi.GET_MOVIE_CATEGORY
import com.technocraze.movie_app_compose.network.MovieApi.GET_REVIEWS
import com.technocraze.movie_app_compose.network.MovieApi.GET_VIDEOS
import com.technocraze.movie_app_compose.utils.Constants.API_LANGUAGE;
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

  //https://api.themoviedb.org/3/movie/{category}
  @GET(GET_MOVIE_CATEGORY)
  suspend fun getMovie(
    @Path("category") category: String,
    @Query("api_key") apiKey: String = BuildConfig.API_KEY,
    @Query("language") language: String = API_LANGUAGE,
    @Query("page") page: Int
  ): MovieResponse

  //https://api.themoviedb.org/3/movie/{movie_id}/videos
  @GET(GET_VIDEOS)
  suspend fun getVideos(
    @Path("movie_id") movieId: Int,
    @Query("api_key") apiKey: String = BuildConfig.API_KEY,
    @Query("language") language: String = API_LANGUAGE,
  ): VideoResponse


  //https://api.themoviedb.org/3/movie/{movie_id}/reviews
  @GET(GET_REVIEWS)
  suspend fun getReviews(
    @Path("movie_id") movieId: Int,
    @Query("api_key") apiKey: String = BuildConfig.API_KEY,
    @Query("language") language: String = API_LANGUAGE,
  ): ReviewResponse
}