package com.technocraze.movie_app_compose.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.technocraze.movie_app_compose.models.Movie
import com.technocraze.movie_app_compose.models.Review
import com.technocraze.movie_app_compose.models.Video
import com.technocraze.movie_app_compose.network.NetworkResult
import com.technocraze.movie_app_compose.repository.MovieRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class MovieDetailIntent {
  class FetchMovieDetails(val movieId: Int) : MovieDetailIntent()
}

@HiltViewModel
class MovieViewModel @Inject constructor(private val movieRepo: MovieRepositoryImpl, application: Application) :
  AndroidViewModel(application) {

  val mainIntent: Channel<MovieDetailIntent> = Channel(Channel.UNLIMITED)

  val movieFlow: Flow<PagingData<Movie>> = movieRepo.getPagedMovies()

  private val _detailState = MutableStateFlow(MovieDetailState())
  val detailState: StateFlow<MovieDetailState>
    get() = _detailState

  init {
    handleIntent()
  }


  private fun handleIntent() {
    viewModelScope.launch {
      mainIntent.consumeAsFlow().collect {
        when (it) {
          is MovieDetailIntent.FetchMovieDetails -> {
            getVideos(it.movieId)
            getReviews(it.movieId)
          }
        }
      }
    }
  }

  private fun getVideos(movieId: Int) {
    viewModelScope.launch(Dispatchers.IO) {
      val videoResponse = movieRepo.getVideos(movieId) //background
      when (videoResponse) {
        is NetworkResult.Success -> {
          _detailState.update {
            _detailState.value.copy(
              videoList = videoResponse.data.results
            )
          }
        }

        is NetworkResult.Error -> {
          _detailState.update {
            _detailState.value.copy(
              videoList = emptyList()
            )
          }
        }
      }
    }
  }

  private fun getReviews(movieId: Int) {
    viewModelScope.launch(Dispatchers.IO) {
      val reviewResponse = movieRepo.getReviews(movieId) //background
      when (reviewResponse) {
        is NetworkResult.Success -> {
          _detailState.update {
            _detailState.value.copy(
              reviewList = reviewResponse.data.results
            )
          }
        }

        is NetworkResult.Error -> {
          _detailState.update {
            _detailState.value.copy(
              reviewList = emptyList()
            )
          }
        }
      }
    }
  }

  data class MovieDetailState(
    var videoList: List<Video> = emptyList(), var reviewList: List<Review> = emptyList()
  )

  override fun onCleared() {
    super.onCleared()
  }


}