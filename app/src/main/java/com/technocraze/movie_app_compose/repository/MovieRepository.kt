package com.technocraze.movie_app_compose.repository

import android.content.Context
import androidx.paging.*
import com.technocraze.movie_app_compose.models.Movie
import com.technocraze.movie_app_compose.models.MovieResponse
import com.technocraze.movie_app_compose.models.ReviewResponse
import com.technocraze.movie_app_compose.models.VideoResponse
import com.technocraze.movie_app_compose.db.MovieDb
import com.technocraze.movie_app_compose.pagging.MovieRemoteMediator
import com.technocraze.movie_app_compose.retrofit.RetrofitHelper
import com.technocraze.movie_app_compose.retrofit.RetrofitInterface
import kotlinx.coroutines.flow.Flow


class MovieRepository(
  val context: Context ,
  private val movieApi: RetrofitInterface,
  private val movieDb: MovieDb,
) {

  suspend fun getMovies(category: String, page: Int = 1): MovieResponse {
    val movieResponse = RetrofitHelper.getInstance().create(RetrofitInterface::class.java).getMovie(
      category = category,
      page = 1
    )
    return movieResponse
  }

  suspend fun getVideos(movieId: Int): VideoResponse {
    val videoResponse = RetrofitHelper.getInstance().create(RetrofitInterface::class.java).getVideos(
      movieId = movieId
    )
    return videoResponse
  }

  suspend fun getReviews(movieId: Int): ReviewResponse {
    val reviewResponse = RetrofitHelper.getInstance().create(RetrofitInterface::class.java).getReviews(
      movieId = movieId
    )
    return reviewResponse
  }

  @OptIn(ExperimentalPagingApi::class)
  fun getPagedMovies(): Flow<PagingData<Movie>> {
    return Pager(

      config = PagingConfig(pageSize = 20, prefetchDistance = 0),
      remoteMediator = MovieRemoteMediator(context,movieDb,movieApi),
      pagingSourceFactory = { movieDb.movieDao().getAllMovies() }
      ).flow
  }

}