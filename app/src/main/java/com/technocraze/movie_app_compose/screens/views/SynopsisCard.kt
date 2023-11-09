package com.technocraze.movie_app_compose.screens.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.technocraze.movie_app_compose.ui.theme.AppTheme

@Composable
fun SynopsisCard(modifier: Modifier = Modifier, movieDescription: String) {
  Card(elevation = AppTheme.dimens.grid_0_25, modifier = modifier) {
    Column(
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.Start,
      modifier = Modifier.padding(AppTheme.dimens.grid_1)
    ) {
      Text(
        text = "Synopsis",
        style = TextStyle(fontSize = 18.sp),
        modifier = Modifier.padding(vertical = AppTheme.dimens.grid_0_25)
      )
      Text(
        text = movieDescription,
        style = TextStyle(fontSize = 12.sp),
        modifier = Modifier.padding(vertical = AppTheme.dimens.grid_0_25)
      )
    }
  }
}