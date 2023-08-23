package com.technocraze.movie_app_compose.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity(tableName = "movie_table")
data class Movie(

  @Expose @SerializedName("adult") var adult: Boolean,
  @Expose @SerializedName("backdrop_path") var backdropPath: String?=null,
  // @Expose @SerializedName("genre_ids") var genreIds: ArrayList<Int> = arrayListOf(),

  @Expose @SerializedName("id") @PrimaryKey(autoGenerate = false) var id: Int,
  @Expose @SerializedName("original_language") var originalLanguage: String?=null,
  @Expose @SerializedName("original_title") var originalTitle: String?=null,
  @Expose @SerializedName("overview") var overview: String?=null,
  @Expose @SerializedName("popularity") var popularity: Double,
  @Expose @SerializedName("poster_path") var posterPath: String?=null,
  @Expose @SerializedName("release_date") var releaseDate: String?=null,
  @Expose @SerializedName("title") var title: String?=null,
  @Expose @SerializedName("video") var video: Boolean,
  @Expose @SerializedName("vote_average") var voteAverage: Double,
  @Expose @SerializedName("vote_count") var voteCount: Int,
  @ColumnInfo(name = "created_at") var createdAt: Long,
  @ColumnInfo(name = "modified_at") var modifiedAt: Long

): Parcelable