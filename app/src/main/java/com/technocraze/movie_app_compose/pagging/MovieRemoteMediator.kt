package com.technocraze.movie_app_compose.pagging

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.technocraze.movie_app_compose.models.Movie
import com.technocraze.movie_app_compose.db.MovieDb
import com.technocraze.movie_app_compose.prefrence.PreferenceHelper
import com.technocraze.movie_app_compose.prefrence.PreferenceHelper.pageKey
import com.technocraze.movie_app_compose.network.ApiService

@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediator(
  val context: Context,
  val movieDb: MovieDb,
  val movieApi: ApiService
): RemoteMediator<Int, Movie>() {

  override suspend fun load(loadType: LoadType, state: PagingState<Int, Movie>): MediatorResult {
    try {
      val lastMovie = state.lastItemOrNull()
      val currentPage = when(loadType){
        LoadType.REFRESH ->  {
          PreferenceHelper.defaultPreference(context = context).pageKey = 1
          1
        }
        LoadType.PREPEND -> {
          return MediatorResult.Success(endOfPaginationReached = true)
        }
        LoadType.APPEND -> {
          val movie: Movie? = lastMovie
          if (movie == null) {
            return MediatorResult.Success(endOfPaginationReached = false)
          } else {
            if (PreferenceHelper.defaultPreference(context = context).pageKey==0) {
              return MediatorResult.Success(endOfPaginationReached = true)
            } else {
              PreferenceHelper.defaultPreference(context = context).pageKey + 1
            }
          }
        }
      }
      val movieResponse = movieApi.getMovie(category = "popular", page = currentPage)
      if (movieResponse.page==null) {
        PreferenceHelper.defaultPreference(context = context).pageKey = 0
      } else {
        PreferenceHelper.defaultPreference(context = context).pageKey = movieResponse.page!!
      }
      movieDb.withTransaction {
        if (loadType == LoadType.REFRESH) {
          movieDb.movieDao().deleteAllMovies()
        }
        for (movie  in movieResponse.results){
          movie.createdAt = System.currentTimeMillis()
        }
        movieDb.movieDao().addMovies(movieResponse.results)
      }
      return MediatorResult.Success(
        endOfPaginationReached = movieResponse.results.isEmpty()
      )
    } catch (e: java.lang.Exception) {
      return MediatorResult.Error(e)
    }
  }


}
