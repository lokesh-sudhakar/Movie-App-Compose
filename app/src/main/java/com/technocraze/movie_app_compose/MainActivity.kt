package com.technocraze.movie_app_compose

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.technocraze.movie_app_compose.models.Movie
import com.technocraze.movie_app_compose.fragments.DetailFragment

interface MovieListener{

  fun onClickMovie(movie: Movie)

  fun popStack()
}
class MainActivity : AppCompatActivity(), MovieListener {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    val navHostFragment = supportFragmentManager.findFragmentById(R.id.container_view) as NavHostFragment

    val navController = navHostFragment.findNavController()


    // supportFragmentManager.beginTransaction().replace(R.id.container_view, HomeFragment()).commit()
  }

  override fun onClickMovie(movie: Movie) {

    supportFragmentManager.beginTransaction().addToBackStack("detail").add(R.id.container_view, DetailFragment.newInstance(movie = movie)).commit()
  }

  override fun popStack() {
    supportFragmentManager.popBackStack()
  }


  override fun onBackPressed() {
    if (supportFragmentManager.backStackEntryCount == 0) {
      super.onBackPressed()
    } else {
      supportFragmentManager.popBackStack()
    }
  }

  // override fun onSupportNavigateUp(): Boolean {
  //   return navControler.navigateUp() || super.onSupportNavigateUp()
  // }
}
