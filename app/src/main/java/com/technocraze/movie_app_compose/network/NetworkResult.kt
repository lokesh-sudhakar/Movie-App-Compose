package com.technocraze.movie_app_compose.network


sealed class NetworkResult<T>(val message: String? = null) {

  class Success<T>(message: String?,val data: T,) : NetworkResult<T>(message)
  class Error<T>(message: String?) : NetworkResult<T>(message)

}