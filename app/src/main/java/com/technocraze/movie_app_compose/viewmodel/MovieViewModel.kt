package com.technocraze.movie_app_compose.viewmodel

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.technocraze.movie_app_compose.models.Movie
import com.technocraze.movie_app_compose.models.Review
import com.technocraze.movie_app_compose.models.Video
import com.technocraze.movie_app_compose.db.MovieDb
import com.technocraze.movie_app_compose.repository.MovieRepository
import com.technocraze.movie_app_compose.retrofit.RetrofitHelper
import com.technocraze.movie_app_compose.retrofit.RetrofitInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieViewModel(application: Application) : AndroidViewModel(application) {

  val movieRepo: MovieRepository = MovieRepository(
    context = application.applicationContext,
    RetrofitHelper.getInstance().create(RetrofitInterface::class.java),
    MovieDb.getInstance(application.applicationContext)
  )

  val movieFlow = movieRepo.getPagedMovies()

  private val _movieList: MutableState<List<Movie>> = mutableStateOf<List<Movie>>(emptyList());
  val movieList: State<List<Movie>>
    get() = _movieList

  private val _videoList: MutableState<List<Video>> = mutableStateOf<List<Video>>(emptyList());
  val videoList: State<List<Video>>
    get() = _videoList

  private val _reviewList: MutableState<List<Review>> = mutableStateOf<List<Review>>(emptyList());
  val reviewList: State<List<Review>>
    get() = _reviewList


  fun getMovies(category: String) {
    viewModelScope.launch(Dispatchers.IO) {
      val movieResponse = movieRepo.getMovies(category);
      if (movieResponse.isSuccess) {
        _movieList.value = movieResponse.results
      } else {
        _movieList.value = emptyList()
      }
    }
  }

  fun getVideos(movieId: Int) {
    viewModelScope.launch(Dispatchers.IO) {
      val videoResponse = movieRepo.getVideos(movieId)
      _videoList.value = videoResponse.results
    }
  }

  fun getReviews(movieId: Int) {
    viewModelScope.launch(Dispatchers.IO) {
      val reviewResponse = movieRepo.getReviews(movieId)
      _reviewList.value = reviewResponse.results
    }
  }


}

class MyAndroidViewModelFactory(private val app: Application) : ViewModelProvider.AndroidViewModelFactory(app) {

  override fun <T : ViewModel> create(modelClass: Class<T>): T {

    if (modelClass.isAssignableFrom(
        MovieViewModel::class.java
      )
    ) {

      return MovieViewModel(app) as T
    }

    throw IllegalArgumentException("Unknown ViewModel class")
  }
}