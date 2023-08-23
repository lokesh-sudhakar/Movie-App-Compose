package com.technocraze.movie_app_compose.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.technocraze.movie_app_compose.HomeScreen
import com.technocraze.movie_app_compose.MovieListener
import com.technocraze.movie_app_compose.ui.theme.Movie_App_ComposeTheme
import com.technocraze.movie_app_compose.viewmodel.MovieViewModel
import com.technocraze.movie_app_compose.viewmodel.MyAndroidViewModelFactory
import androidx.paging.compose.collectAsLazyPagingItems


class HomeFragment : Fragment() {

  lateinit var movieListener: MovieListener

  private val movieViewModel: MovieViewModel by viewModels {
    MyAndroidViewModelFactory(requireActivity().application)
  }

  override fun onAttach(context: Context) {
    super.onAttach(context)
    try {
      movieListener = context as MovieListener
    } catch (castException: ClassCastException) {
      /** The activity does not implement the listener.  */
    }
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {


    val composeView = ComposeView(requireContext())


    composeView.apply {
      setContent {
        val getAllMovies = movieViewModel.movieFlow.collectAsLazyPagingItems()
        Movie_App_ComposeTheme {
          // A surface container using the 'background' color from the theme
          Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
            HomeScreen(getAllMovies) { movie ->
              val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(movie)
              findNavController().navigate(action)

            }
          }
        }
      }
    }
    return composeView
  }

  fun moveToDetailFrag() {
    movieListener.popStack()
  }

}