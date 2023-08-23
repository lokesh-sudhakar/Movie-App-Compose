package com.technocraze.movie_app_compose.models

import com.google.gson.annotations.SerializedName


data class ReviewResponse (

  @SerializedName("id"            ) var id           : Int?               = null,
  @SerializedName("page"          ) var page         : Int?               = null,
  @SerializedName("results"       ) var results      : ArrayList<Review> = arrayListOf(),
  @SerializedName("total_pages"   ) var totalPages   : Int?               = null,
  @SerializedName("total_results" ) var totalResults : Int?               = null

)