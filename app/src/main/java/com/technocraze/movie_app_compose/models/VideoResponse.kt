package com.technocraze.movie_app_compose.models

import com.google.gson.annotations.SerializedName


data class VideoResponse(

  @SerializedName("id") var id: Int? = null,
  @SerializedName("results") var results: ArrayList<Video> = arrayListOf()

)