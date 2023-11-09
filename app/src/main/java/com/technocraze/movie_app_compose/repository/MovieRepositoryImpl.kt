package com.technocraze.movie_app_compose.repository

import android.util.Log
import androidx.paging.*
import com.technocraze.movie_app_compose.BuildConfig
import com.technocraze.movie_app_compose.db.MovieDb
import com.technocraze.movie_app_compose.models.Movie
import com.technocraze.movie_app_compose.models.MovieResponse
import com.technocraze.movie_app_compose.models.ReviewResponse
import com.technocraze.movie_app_compose.models.VideoResponse
import com.technocraze.movie_app_compose.pagging.MovieRemoteMediator
import com.technocraze.movie_app_compose.network.ApiService
import com.technocraze.movie_app_compose.network.NetworkResult
import com.technocraze.movie_app_compose.utils.Constants
import com.technocraze.movie_app_compose.utils.Constants.PAGE_SIZE
import kotlinx.coroutines.flow.Flow


class MovieRepositoryImpl(
  private val movieApi: ApiService,
  private val movieDb: MovieDb,
  private val movieRemoteMediator: MovieRemoteMediator
) : MovieRepository {
  private val TAG = "MovieRepository"

  override suspend fun getMovies(category: String, page: Int): NetworkResult<MovieResponse> {
    return try {
      val movies = movieApi.getMovie(
        category = category,
        page = page,
      )
      NetworkResult.Success("Success", movies);
    } catch (e: Exception) {
      Log.d(TAG, "getVideos: ${e.message}")
      NetworkResult.Error("Something went wrong")
    }
  }

  override suspend fun getVideos(movieId: Int): NetworkResult<VideoResponse> {
    return try {
      val trailerResponse = movieApi.getVideos(
        movieId = movieId)
      NetworkResult.Success("Success", trailerResponse);
    } catch (e: Exception) {
      Log.d(TAG, "getVideos: ${e.message}")
      NetworkResult.Error("Something went wrong")
    }
  }

  override suspend fun getReviews(movieId: Int): NetworkResult<ReviewResponse> {
    return try {
      val movieReviews = movieApi.getReviews(
        movieId = movieId)
      NetworkResult.Success("Success", movieReviews);
    } catch (e: Exception) {
      Log.d(TAG, "getVideos: ${e.message}")
      NetworkResult.Error("Something went wrong")
    }
  }

  @OptIn(ExperimentalPagingApi::class)
  override fun getPagedMovies(): Flow<PagingData<Movie>> {
    return Pager(
      config = PagingConfig(pageSize = PAGE_SIZE, prefetchDistance = 0),
      remoteMediator = movieRemoteMediator,
      pagingSourceFactory = { movieDb.movieDao().getAllMovies() }
    ).flow
  }

}