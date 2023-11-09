package com.technocraze.movie_app_compose.di

import android.content.Context
import com.technocraze.movie_app_compose.db.MovieDb
import com.technocraze.movie_app_compose.pagging.MovieRemoteMediator
import com.technocraze.movie_app_compose.repository.MovieRepositoryImpl
import com.technocraze.movie_app_compose.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

  @Provides
  fun getMovieRepo(
    movieApi: ApiService,
    movieDb: MovieDb, movieRemoteMediator: MovieRemoteMediator
  ): MovieRepositoryImpl {
    return MovieRepositoryImpl(movieApi, movieDb, movieRemoteMediator)
  }

  @Singleton
  @Provides
  fun getMovieDb(@ApplicationContext context: Context): MovieDb {
    return MovieDb.getInstance(context);
  }

  @Singleton
  @Provides
  fun getMovieRemoteMediator(
    @ApplicationContext context: Context,
    movieApi: ApiService,
    movieDb: MovieDb
  ): MovieRemoteMediator {
    return MovieRemoteMediator(context, movieDb, movieApi)
  }

}