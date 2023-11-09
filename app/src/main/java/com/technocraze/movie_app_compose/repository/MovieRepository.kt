package com.technocraze.movie_app_compose.repository

import androidx.paging.PagingData
import com.technocraze.movie_app_compose.models.Movie
import com.technocraze.movie_app_compose.models.MovieResponse
import com.technocraze.movie_app_compose.models.ReviewResponse
import com.technocraze.movie_app_compose.models.VideoResponse
import com.technocraze.movie_app_compose.network.NetworkResult
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

  suspend fun getMovies(category: String, page: Int =1): NetworkResult<MovieResponse>
  suspend fun getVideos(movieId: Int): NetworkResult<VideoResponse>

  suspend fun getReviews(movieId: Int): NetworkResult<ReviewResponse>

  fun getPagedMovies(): Flow<PagingData<Movie>>
}