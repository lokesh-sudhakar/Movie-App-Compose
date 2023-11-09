package com.technocraze.movie_app_compose.navigation

import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.technocraze.movie_app_compose.screens.DetailScreen
import com.technocraze.movie_app_compose.screens.HomeScreen
import com.technocraze.movie_app_compose.models.Movie


@Composable
fun NavGraph(navController: NavHostController) {
  val movieJsonKey = "movieJson"
  NavHost(navController = navController, startDestination = Routes.Home.route) {
    composable(
      Routes.Detail.route + "/{$movieJsonKey}",
      arguments = listOf(navArgument(movieJsonKey) { type = NavType.StringType })
    ) {
      val json = Uri.decode(it.arguments?.getString(movieJsonKey))
      DetailScreen(
        navController, Gson().fromJson(json, Movie::class.java),
        getActivityViewModel()
      )
    }
    composable(Routes.Home.route) {
      HomeScreen(navController = navController, getActivityViewModel())
    }

  }
}

@Composable
inline fun <reified T : ViewModel> getActivityViewModel(): T =
  viewModel(viewModelStoreOwner = LocalContext.current as ComponentActivity)

fun NavHostController.navigateAndClean(route: String) {
  navigate(route = route) {
    popUpTo(graph.startDestinationId) { inclusive = true }
  }
  graph.setStartDestination(route)
}