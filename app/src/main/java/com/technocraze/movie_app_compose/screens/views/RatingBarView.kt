package com.technocraze.movie_app_compose.screens.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.technocraze.movie_app_compose.R
import com.technocraze.movie_app_compose.ui.theme.AppTheme


@Composable
fun RatingBarView(modifier: Modifier = Modifier, rating: Float, maxRating: Int = 5) {
  Row(
    modifier = modifier,
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.Start
  ) {
    for (i in 1..maxRating) {
      Icon(
        painter = painterResource(id = R.drawable.star_filled),
        contentDescription = null,
        modifier = Modifier.size(AppTheme.dimens.grid_1_5),
        tint = if (i < rating) Color(0xFFFF9529) else Color.LightGray
      )
    }
  }
}
