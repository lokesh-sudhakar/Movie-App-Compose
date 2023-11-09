package com.technocraze.movie_app_compose.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.technocraze.movie_app_compose.utils.Constants
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity(tableName = Constants.MOVIE_TABLE_NAME)
data class Movie(

  @Expose @SerializedName("adult") var adult: Boolean=false,
  @Expose @SerializedName("backdrop_path") var backdropPath: String?=null,
  @Expose @SerializedName("id") @PrimaryKey(autoGenerate = false) var id: Int,
  @Expose @SerializedName("original_language") var originalLanguage: String?=null,
  @Expose @SerializedName("original_title") var originalTitle: String?=null,
  @Expose @SerializedName("overview") var overview: String?=null,
  @Expose @SerializedName("popularity") var popularity: Double,
  @Expose @SerializedName("poster_path") var posterPath: String?=null,
  @Expose @SerializedName("release_date") var releaseDate: String?=null,
  @Expose @SerializedName("title") var title: String?=null,
  @Expose @SerializedName("video") var video: Boolean = false,
  @Expose @SerializedName("vote_average") var voteAverage: Double =0.0,
  @Expose @SerializedName("vote_count") var voteCount: Int=0,
  @ColumnInfo(name = "created_at") var createdAt: Long=0L,
  @ColumnInfo(name = "modified_at") var modifiedAt: Long = 0L

): Parcelable