package com.technocraze.movie_app_compose.db
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.technocraze.movie_app_compose.models.Movie
import com.technocraze.movie_app_compose.utils.Constants

@Database(entities = [Movie::class], version = 2, )
abstract class MovieDb: RoomDatabase() {

  abstract fun movieDao(): MovieDao

  companion object{
    fun getInstance(context: Context): MovieDb {
      return Room.databaseBuilder(context =context ,MovieDb::class.java, Constants.DATABASE_NAME).build()
    }
  }

}