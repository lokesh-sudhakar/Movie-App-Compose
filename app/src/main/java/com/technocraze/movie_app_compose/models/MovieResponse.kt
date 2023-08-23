package com.technocraze.movie_app_compose.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class MovieResponse(
  var isSuccess: Boolean = true,
  var message: String = "",
  @Expose @SerializedName("page") var page: Int? = null,
  @Expose @SerializedName("results") var results: ArrayList<Movie> = arrayListOf(),
  @Expose @SerializedName("total_pages") var totalPages: Int? = null,
  @Expose @SerializedName("total_results") var totalResults: Int? = null

)