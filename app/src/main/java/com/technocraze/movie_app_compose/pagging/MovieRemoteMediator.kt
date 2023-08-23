package com.technocraze.movie_app_compose.pagging

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.technocraze.movie_app_compose.models.Movie
import com.technocraze.movie_app_compose.db.MovieDb
import com.technocraze.movie_app_compose.prefrence.PreferenceHelper
import com.technocraze.movie_app_compose.prefrence.PreferenceHelper.pageKey
import com.technocraze.movie_app_compose.retrofit.RetrofitInterface

@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediator(
  val context: Context,
  val movieDb: MovieDb,
  val movieApi: RetrofitInterface
): RemoteMediator<Int, Movie>() {

  override suspend fun load(loadType: LoadType, state: PagingState<Int, Movie>): MediatorResult {
    try {
      val lastMovie = state.lastItemOrNull()
      lastMovie?.let {
        // Toast.makeText(context,"last movie visible: ${it.title}",Toast.LENGTH_SHORT).show()
      }
      val currentPage = when(loadType){
        LoadType.REFRESH ->  {
          PreferenceHelper.defaultPreference(context = context).pageKey = 1
          1
        }
        LoadType.PREPEND -> {
          return MediatorResult.Success(endOfPaginationReached = true)
        }
        LoadType.APPEND -> {
          val movie: Movie? = state.lastItemOrNull()
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
      // Toast.makeText(context,"page no: $currentPage",Toast.LENGTH_SHORT).show()
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
      Log.i("error_remote", "load: "+e)

      return MediatorResult.Error(e)
    }
  }


}
