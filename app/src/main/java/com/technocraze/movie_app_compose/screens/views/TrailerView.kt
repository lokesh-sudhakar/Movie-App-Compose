package com.technocraze.movie_app_compose.screens.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.technocraze.movie_app_compose.R
import com.technocraze.movie_app_compose.models.Video
import com.technocraze.movie_app_compose.ui.theme.AppTheme
import com.technocraze.movie_app_compose.utils.AppUtils.getYoutubeThumbnailUrl

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TrailerView(modifier: Modifier = Modifier,
  videoList: List<Video>, onVideoClick: (String?) -> (Unit)) {
  Card(elevation = AppTheme.dimens.grid_0_25, modifier = modifier) {
    Column() {
      Text(
        text = "Trailers",
        style = TextStyle(fontSize = 18.sp),
        modifier = Modifier.padding(horizontal = AppTheme.dimens.grid_2, vertical = AppTheme.dimens.grid_0_25)
      )
      LazyRow(
        modifier = Modifier
          .fillMaxWidth()
          .padding(AppTheme.dimens.grid_1_5),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
      ) {
        items(videoList.size) { ind ->
          Card(
            modifier = Modifier
              .width(AppTheme.dimens.trailerCardWidth)
              .height(AppTheme.dimens.trailerCardHeight),
            elevation = AppTheme.dimens.grid_0_25,
            onClick = { onVideoClick(videoList[ind].key) }
          ) {
            Box(
              modifier = Modifier.fillMaxSize(),
              contentAlignment = Alignment.Center
            ) {
              val thumbnailUrl = getYoutubeThumbnailUrl(videoList[ind].key)
              Image(
                modifier = Modifier.fillMaxSize(),
                painter = rememberImagePainter(data = thumbnailUrl) {
                  error(R.drawable.ic_placeholder)
                  placeholder(R.drawable.ic_placeholder)
                },
                contentDescription = "Unsplash Image",
                contentScale = ContentScale.Crop
              )
              Icon(
                painter = painterResource(id = R.drawable.play_filled),
                contentDescription = null,
                tint = Color.White, modifier = Modifier.size(AppTheme.dimens.grid_6)
              )
            }
          }
          Spacer(modifier = Modifier.width(AppTheme.dimens.grid_2_5))
        }
      }
    }
  }
}