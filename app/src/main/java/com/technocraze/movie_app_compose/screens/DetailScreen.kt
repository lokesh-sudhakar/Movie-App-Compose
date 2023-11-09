package com.technocraze.movie_app_compose.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.technocraze.movie_app_compose.BuildConfig
import com.technocraze.movie_app_compose.R
import com.technocraze.movie_app_compose.models.Movie
import com.technocraze.movie_app_compose.navigation.Routes
import com.technocraze.movie_app_compose.navigation.navigateAndClean
import com.technocraze.movie_app_compose.screens.views.RatingBarView
import com.technocraze.movie_app_compose.screens.views.ReviewView
import com.technocraze.movie_app_compose.screens.views.SynopsisCard
import com.technocraze.movie_app_compose.screens.views.TrailerView
import com.technocraze.movie_app_compose.ui.theme.AppTheme
import com.technocraze.movie_app_compose.utils.AppUtils.getYoutubeString
import com.technocraze.movie_app_compose.utils.AppUtils.launchYoutubeVideoIntent
import com.technocraze.movie_app_compose.utils.AppUtils.shareTextIntent
import com.technocraze.movie_app_compose.viewmodel.MovieDetailIntent
import com.technocraze.movie_app_compose.viewmodel.MovieViewModel
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState


@Composable
fun DetailScreen(
  navController: NavHostController,
  movie: Movie, viewModel: MovieViewModel
) {
  val context = LocalContext.current
  LaunchedEffect(key1 = null) {
    viewModel.mainIntent.send(MovieDetailIntent.FetchMovieDetails(movie.id))
  }
  val state by viewModel.detailState.collectAsState()
  val launcher = rememberLauncherForActivityResult(
    ActivityResultContracts.StartActivityForResult()
  ) {}
  DetailUi(movie, state,
    onVideoClick = { youtubeID ->
      launcher.launch(launchYoutubeVideoIntent(context, youtubeID))
    },
    backNav = {
      navController.navigateAndClean(Routes.Home.route)
    },
    onShareClicked = { youtubeID ->
      launcher.launch(getYoutubeString(youtubeID).shareTextIntent())
    }
  )
}

@Composable
fun DetailUi(
  movie: Movie,
  detailState: MovieViewModel.MovieDetailState,
  onVideoClick: (String?) -> (Unit),
  backNav: () -> Unit,
  onShareClicked: (String?) -> (Unit)
) {
  val state = rememberCollapsingToolbarScaffoldState()
  CollapsingToolbarScaffold(
    modifier = Modifier,
    state = state,
    scrollStrategy = ScrollStrategy.ExitUntilCollapsed,
    toolbar = {
      val textSize = (18 + (20 - 12) * state.toolbarState.progress).sp
      val textStartPadding = (16 + (20 * (1 - state.toolbarState.progress))).dp
      val imageAlpha: Float by animateFloatAsState(
        targetValue = state.toolbarState.progress,
        animationSpec = tween(
          durationMillis = 500,
          easing = LinearEasing,
        ),
        label = "",
      )
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(AppTheme.dimens.BackDropImageHeight)
          .pin()
          .background(MaterialTheme.colors.primary)
      ) {
        Image(
          modifier = Modifier.fillMaxSize(),
          painter = rememberImagePainter(data = BuildConfig.BASE_POSTER_PATH_URL + movie.backdropPath) {
            error(R.drawable.ic_placeholder)
            placeholder(R.drawable.ic_placeholder)
          },
          alpha = imageAlpha,
          contentDescription = "Unsplash Image",
          contentScale = ContentScale.Crop
        )
        Box(
          modifier = Modifier
            .fillMaxSize()
            .background(
              brush = Brush.verticalGradient(
                colors = listOf(Color(0xD9000000),Color.Transparent), endY = 1.0f,
              )
            )
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
      }
      Text(
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
        text = movie.title.toString(),
        style = TextStyle(color = Color.White, fontSize = textSize),
        modifier = Modifier
          .padding(AppTheme.dimens.grid_2)
          .padding(start = textStartPadding.value.dp, end = textStartPadding.value.dp)
          .road(whenCollapsed = Alignment.TopStart, whenExpanded = Alignment.BottomStart),
      )
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(AppTheme.dimens.grid_0_5), horizontalArrangement = Arrangement.SpaceBetween
      ) {
        IconButton(onClick = { backNav() }) {
          Icon(Icons.Default.ArrowBack, contentDescription = null, tint = Color.White)
        }
        IconButton(onClick = {
          onShareClicked(
            if (detailState.videoList.isNotEmpty()) detailState.videoList[0].key
            else movie.title
          )
        }) {
          Icon(Icons.Default.Share, contentDescription = null, tint = Color.White)
        }
      }
    }, body = {
      LazyColumn(modifier = Modifier.padding(AppTheme.dimens.grid_0_25).fillMaxHeight()) {
        item() {
          Column(modifier = Modifier.padding(AppTheme.dimens.grid_1_5)) {
            Text(
              text = movie.releaseDate.toString().substring(0, 4),
              style = TextStyle(fontSize = 10.sp),
              modifier = Modifier.padding(vertical = AppTheme.dimens.grid_0_25)
            )
            Text(
              text = movie.title.toString(),
              style = TextStyle(fontSize = 18.sp),
              modifier = Modifier.padding(vertical = AppTheme.dimens.grid_0_25)
            )
            if (detailState.reviewList.isNotEmpty()) {
              RatingBarView(
                modifier = Modifier.padding(vertical = AppTheme.dimens.grid_0_25),
                rating = (movie.voteAverage / 2).toFloat()
              )
            }
          }
          SynopsisCard(movieDescription = movie.overview.toString(), modifier = Modifier.padding(AppTheme.dimens.grid_0_5))
          if (detailState.videoList.isNotEmpty()){
            TrailerView(videoList = detailState.videoList, onVideoClick = onVideoClick, modifier = Modifier.padding(AppTheme.dimens.grid_0_5))
          }
        }
        if (detailState.reviewList.isNotEmpty()) {
          item {
            Text(
              text = "Reviews",
              style = TextStyle(fontSize = 18.sp),
              modifier = Modifier.padding(horizontal = AppTheme.dimens.grid_2, vertical = AppTheme.dimens.grid_0_25)
            )
          }
          items(detailState.reviewList.size) { ind ->
            ReviewView(review = detailState.reviewList[ind])
          }
        }
      }
    })
}



