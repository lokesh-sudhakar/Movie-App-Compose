package com.technocraze.movie_app_compose.screens.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.technocraze.movie_app_compose.BuildConfig
import com.technocraze.movie_app_compose.R
import com.technocraze.movie_app_compose.models.Movie
import com.technocraze.movie_app_compose.screens.getTitle
import com.technocraze.movie_app_compose.ui.theme.AppTheme

@OptIn(ExperimentalCoilApi::class)
@Composable
fun MovieCard(
  movie: Movie,
  onMovieClick: (Movie) -> (Unit),
  modifier: Modifier = Modifier
) {
  val showShimmer = remember {
    mutableStateOf(true)
  }
  Box(
    modifier = modifier
      .fillMaxWidth()
      .height(AppTheme.dimens.posterCardHeight)
      .clickable {
        onMovieClick(movie)
      },
  ) {

    Image(
      modifier = Modifier
        .fillMaxSize(),
      painter = rememberImagePainter(data = BuildConfig.BASE_POSTER_PATH_URL + movie.posterPath) {
        error(R.drawable.ic_placeholder)
        placeholder(R.drawable.ic_placeholder)
        // listener(object : ImageRequest.Listener {
        //   override fun onSuccess(request: ImageRequest, metadata: ImageResult.Metadata) {
        //     super.onSuccess(request, metadata)
        //     showShimmer.value = false
        //   }
        //
        //   override fun onStart(request: ImageRequest) {
        //     super.onStart(request)
        //     showShimmer.value = true
        //   }
        //
        //   override fun onError(request: ImageRequest, throwable: Throwable) {
        //     super.onError(request, throwable)
        //     showShimmer.value = false
        //   }
        // })
      },

      contentDescription = "Unsplash Image",
      contentScale = ContentScale.Crop
    )
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
        modifier = Modifier.padding(horizontal = AppTheme.dimens.grid_1_5, vertical = AppTheme.dimens.grid_0_5)
      )
    }
  }
}