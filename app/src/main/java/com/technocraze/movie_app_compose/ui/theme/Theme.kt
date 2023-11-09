package com.technocraze.movie_app_compose.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalConfiguration


private val DarkColorPalette = darkColors(
  primary = Purple80,
  primaryVariant = PurpleGrey80,
  secondary = Pink80
)

private val LightColorPalette = lightColors(
  primary = Purple40,
  primaryVariant = PurpleGrey40,
  secondary = Pink40

  /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)
// private val DarkColorPalette = darkColors(
//   primary = Purple200,
//   primaryVariant = Purple700,
//   secondary = Teal200
// )
//
// private val LightColorPalette = lightColors(
//   primary = Purple500,
//   primaryVariant = Purple700,
//   secondary = Teal200
//
//   /* Other default colors to override
//     background = Color.White,
//     surface = Color.White,
//     onPrimary = Color.White,
//     onSecondary = Color.Black,
//     onBackground = Color.Black,
//     onSurface = Color.Black,
//     */
// )

@Composable
fun ProvideDimens(
  dimensions: Dimensions,
  content: @Composable () -> Unit
) {
  val dimensionSet = remember { dimensions }
  CompositionLocalProvider(LocalAppDimens provides dimensionSet, content = content)
}

private val LocalAppDimens = staticCompositionLocalOf {
  smallDimensions
}

@Composable
fun Movie_App_ComposeTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
  val colors = if (darkTheme) {
    DarkColorPalette
  } else {
    LightColorPalette
  }
  val configuration = LocalConfiguration.current
  val dimensions = if (configuration.screenWidthDp <= 360) smallDimensions else sw360Dimensions
  ProvideDimens(dimensions = dimensions) {
    MaterialTheme(
      colors = colors,
      typography = Typography,
      shapes = Shapes,
      content = content
    )
  }
}

object AppTheme {
  val dimens: Dimensions
    @Composable
    get() = LocalAppDimens.current
}

val Dimens: Dimensions
  @Composable
  get() = AppTheme.dimens