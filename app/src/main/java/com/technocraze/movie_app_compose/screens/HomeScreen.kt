package com.technocraze.movie_app_compose.screens

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.google.gson.Gson
import com.technocraze.movie_app_compose.models.Movie
import com.technocraze.movie_app_compose.navigation.Routes
import com.technocraze.movie_app_compose.screens.views.MovieCard
import com.technocraze.movie_app_compose.viewmodel.MovieViewModel

@Composable
fun HomeScreen(
  navController: NavController,
  movieViewModel: MovieViewModel,
) {
  val getAllMovies = movieViewModel.movieFlow.collectAsLazyPagingItems()
  HomeUi(flowMovieList = getAllMovies, onMovieClick = { movie ->
    val movieJson = Uri.encode(Gson().toJson(movie))
    navController.navigate(Routes.Detail.route + "/$movieJson")
  })
}

@Composable
fun HomeUi(flowMovieList: LazyPagingItems<Movie>, onMovieClick: (Movie) -> (Unit)) {
  val lazyGridState = rememberLazyGridState()
  Scaffold(
    topBar = {
      TopAppBar(title = { Text(text = "Movies") })
    }, content = {
      it
      LazyVerticalGrid(
        state = lazyGridState,
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalArrangement = Arrangement.Center,
        userScrollEnabled = true,

        ) {

        items(count = flowMovieList.itemCount) { index ->
          if (flowMovieList[index] != null) {
            MovieCard(flowMovieList[index]!!, onMovieClick)
          }
        }
      }
    }
  )
}


fun getTitle(title: String): String {
  return if (title.length > 10)
    title.subSequence(0, 10).toString()
  else
    title
}