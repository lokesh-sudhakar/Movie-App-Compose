package com.technocraze.movie_app_compose.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.technocraze.movie_app_compose.models.Movie

@Dao
abstract class MovieDao {

  @Query("SELECT * FROM movie_table ORDER BY created_at ASC")
  abstract fun getAllMovies(): PagingSource<Int, Movie>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  abstract suspend fun addMovies(movies: List<Movie>)

  @Query("DELETE FROM movie_table")
  abstract suspend fun deleteAllMovies()

}