package com.technocraze.movie_app_compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems

import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.technocraze.movie_app_compose.models.Movie

@Composable
fun HomeScreen(flowMovieList: LazyPagingItems<Movie>, onMovieClick: (Movie) -> (Unit)) {

  Scaffold(
    topBar = {
      TopAppBar(title = { Text(text = "Movies") })
    }, content = {
      it
      val lazyGridState = rememberLazyGridState()

      LazyVerticalGrid(
        state = lazyGridState,
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalArrangement = Arrangement.Center,
      ) {
        items(count = flowMovieList.itemCount) { index ->
          if (flowMovieList[index]!=null) {
            MovieCard(flowMovieList[index]!!, onMovieClick)
          }
        }
      }
    }
  )
}

@OptIn( ExperimentalCoilApi::class)
@Composable
fun MovieCard(movie: Movie, onMovieClick: (Movie) -> (Unit)) {
  val BASE_URL_FOR_POSTERPATH = "https://image.tmdb.org/t/p/w342/"
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .height(260.dp)
      .clickable {
        onMovieClick(movie)
      },
  ) {
    Image( modifier = Modifier.fillMaxSize(),
      painter = rememberImagePainter(data = BASE_URL_FOR_POSTERPATH + movie.posterPath) {
        error(R.drawable.ic_placeholder)
        placeholder(R.drawable.ic_placeholder)
      },
      contentDescription = "Unsplash Image",
      contentScale = ContentScale.Crop )

    Box(
      modifier = Modifier
        .fillMaxSize()
        .background(
          brush = Brush.verticalGradient(
            colors = listOf(Color.Transparent, Color(0xD9000000)), startY = 1.0f
          )
        )
    )
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .align(Alignment.BottomStart)
    ) {
      Text(
        text = getTitle(movie.title!!),
        color = Color.White,
        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
      )
    }
  }
}

fun getTitle(title: String): String {
  if (title.length > 10)
    return title.subSequence(0, 10).toString()
  else
    return title
}