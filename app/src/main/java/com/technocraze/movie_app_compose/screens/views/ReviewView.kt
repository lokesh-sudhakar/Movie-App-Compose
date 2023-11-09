package com.technocraze.movie_app_compose.screens.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.technocraze.movie_app_compose.models.Review
import com.technocraze.movie_app_compose.ui.theme.AppTheme


@Composable
fun ReviewView(modifier: Modifier = Modifier,review: Review) {
  Column(
    modifier = modifier
      .fillMaxWidth()
      .padding(AppTheme.dimens.grid_1_5)
  ) {
    Text(
      text = review.author.toString(),
      style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.SemiBold),
      modifier = Modifier.padding(vertical = AppTheme.dimens.grid_0_25)
    )
    Text(
      text = review.content.toString(),
      maxLines = 5,
      overflow = TextOverflow.Ellipsis,
      style = TextStyle(fontSize = 14.sp, fontStyle = FontStyle.Italic),
      modifier = Modifier.padding(vertical = AppTheme.dimens.grid_0_25)
    )
  }
}