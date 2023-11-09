package com.technocraze.movie_app_compose.navigation

sealed class Routes(val route: String) {
  object Home : Routes("home")
  object Detail : Routes("detail")

}