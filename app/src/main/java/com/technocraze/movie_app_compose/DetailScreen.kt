package com.technocraze.movie_app_compose

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.technocraze.movie_app_compose.models.Movie
import com.technocraze.movie_app_compose.models.Review
import com.technocraze.movie_app_compose.models.Video
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

@Composable
fun DetailScreen(
  movie: Movie,
  videoList: List<Video>,
  reviewList: List<Review>,
  onVideoClick: (String?) -> (Unit),
  backNav: () -> Unit,
  onShareClicked: (String?) -> (Unit)
) {
  val BASE_URL_FOR_POSTERPATH = "https://image.tmdb.org/t/p/w342/"
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
        )
      )
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(280.dp)
          .pin()
          .background(MaterialTheme.colors.primary)
      ) {
        Image( modifier = Modifier.fillMaxSize(),
          painter = rememberImagePainter(data = BASE_URL_FOR_POSTERPATH + movie.backdropPath) {
            error(R.drawable.ic_placeholder)
            placeholder(R.drawable.ic_placeholder)
          },
          contentDescription = "Unsplash Image",
          contentScale = ContentScale.Crop )
      }
      Text(
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
        text = movie.title.toString(),
        style = TextStyle(color = Color.White, fontSize = textSize),
        modifier = Modifier
          .padding(16.dp)
          .padding(start = textStartPadding.value.dp, end = textStartPadding.value.dp)
          .road(whenCollapsed = Alignment.TopStart, whenExpanded = Alignment.BottomStart),
      )
      Row(modifier = Modifier
        .fillMaxWidth()
        .padding(4.dp), horizontalArrangement = Arrangement.SpaceBetween) {
        IconButton(onClick = { backNav() }) {
          Icon(Icons.Default.ArrowBack, contentDescription = null, tint = Color.White)
        }
        IconButton(onClick = { onShareClicked(if (videoList.isNotEmpty()) videoList[0].key else  movie.title) }) {
          Icon(Icons.Default.Share, contentDescription = null, tint = Color.White)
        }
      }

    }, body = {
      LazyColumn(modifier = Modifier.padding(2.dp)) {
        item() {
          Column(modifier = Modifier.padding(12.dp)) {
            Text(
              text = movie.releaseDate.toString().substring(0, 4),
              style = TextStyle(fontSize = 10.sp),
              modifier = Modifier.padding(vertical = 2.dp)
            )
            Text(
              text = movie.title.toString(),
              style = TextStyle(fontSize = 18.sp),
              modifier = Modifier.padding(vertical = 2.dp)
            )
            RatingBar(
              modifier = Modifier.padding(vertical = 2.dp),
              rating = if (movie.voteAverage == null) 0f else (movie.voteAverage!! / 2).toFloat()
            )
          }
          SynopsisCard(movieDescription = movie.overview.toString(), modifier = Modifier.padding(4.dp))
          Text(
            text = "Trailers",
            style = TextStyle(fontSize = 18.sp),
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 2.dp)
          )
          TrailerView(videoList, onVideoClick)
        }
        item {
          Text(
            text = "Reviews",
            style = TextStyle(fontSize = 18.sp),
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 2.dp)
          )
        }
        items(reviewList.size) { ind ->
          ReviewView(review = reviewList[ind])
        }
      }
    })
}

@Composable
private fun ReviewView(review: Review) {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .padding(12.dp)
  ) {
    Text(
      text = review.author.toString(),
      style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.SemiBold),
      modifier = Modifier.padding(vertical = 2.dp)
    )
    Text(
      text = review.content.toString(),
      maxLines = 5,
      overflow = TextOverflow.Ellipsis,
      style = TextStyle(fontSize = 14.sp, fontStyle = FontStyle.Italic),
      modifier = Modifier.padding(vertical = 2.dp)
    )
  }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TrailerView(videoList: List<Video>, onVideoClick: (String?) -> (Unit)) {
  LazyRow(
    modifier = Modifier
      .fillMaxWidth()
      .padding(12.dp),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.Start
  ) {
    items(videoList.size) { ind ->
      Card(
        modifier = Modifier
          .width(200.dp)
          .height(150.dp), elevation = 2.dp, onClick = { onVideoClick(videoList[ind].key) }
      ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
          val thumbnailUrl = "https://i.ytimg.com/vi/${videoList[ind].key}/hqdefault.jpg"
          Image( modifier = Modifier.fillMaxSize(),
            painter = rememberImagePainter(data = thumbnailUrl) {
              error(R.drawable.ic_placeholder)
              placeholder(R.drawable.ic_placeholder)
            },
            contentDescription = "Unsplash Image",
            contentScale = ContentScale.Crop )
          Icon(
            painter = painterResource(id = R.drawable.play_filled),
            contentDescription = null,
            tint = Color.White, modifier = Modifier.size(50.dp)
          )
        }
      }
      Spacer(modifier = Modifier.width(20.dp))
    }
  }
}

@Composable
fun SynopsisCard(modifier: Modifier = Modifier, movieDescription: String) {
  Card(elevation = 2.dp, modifier = modifier) {
    Column(
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.Start,
      modifier = Modifier.padding(8.dp)
    ) {
      Text(text = "Synopsis", style = TextStyle(fontSize = 18.sp), modifier = Modifier.padding(vertical = 2.dp))
      Text(text = movieDescription, style = TextStyle(fontSize = 12.sp), modifier = Modifier.padding(vertical = 2.dp))
    }
  }
}

@Composable
fun RatingBar(modifier: Modifier = Modifier, rating: Float, maxRating: Int = 5) {
  val rateState = remember {
    mutableStateOf(rating)
  }
  Row(
    modifier = modifier,
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.Start
  ) {
    for (i in 1..maxRating) {
      Icon(
        painter = painterResource(id = R.drawable.star_filled),
        contentDescription = null,
        modifier = Modifier.size(12.dp),
        tint = if (i < rating) Color(0xFFFF9529) else Color.LightGray
      )

    }
  }


}