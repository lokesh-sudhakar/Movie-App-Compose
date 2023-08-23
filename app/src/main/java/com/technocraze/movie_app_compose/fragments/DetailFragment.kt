package com.technocraze.movie_app_compose.fragments

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
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
import androidx.navigation.fragment.navArgs
import com.technocraze.movie_app_compose.DetailScreen
import com.technocraze.movie_app_compose.MovieListener
import com.technocraze.movie_app_compose.models.Movie
import com.technocraze.movie_app_compose.ui.theme.Movie_App_ComposeTheme
import com.technocraze.movie_app_compose.viewmodel.MovieViewModel
import com.technocraze.movie_app_compose.viewmodel.MyAndroidViewModelFactory

private const val ARG_MOVIE = "movie"

/**
 * A simple [Fragment] subclass.
 * Use the [DetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailFragment : Fragment() {

  lateinit var movieListener: MovieListener;
  private  val args :  DetailFragmentArgs by navArgs()

  private var movie: Movie? = null

  override fun onAttach(context: Context) {
    super.onAttach(context)
    try {
      movieListener = context as MovieListener
    } catch (castException: ClassCastException) {
      /** The activity does not implement the listener.  */
    }
  }

  companion object {

    fun newInstance(movie: Movie): DetailFragment {
      val fragment = DetailFragment().apply {
        arguments = Bundle().apply {
          putParcelable(ARG_MOVIE, movie)
        }
      }
      return fragment
    }

  }

  fun getArg() {
    val bundle = requireArguments()
    if (bundle.containsKey(ARG_MOVIE)) {
      movie = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        bundle.getParcelable(ARG_MOVIE, Movie::class.java)
      } else {
        bundle.getParcelable<Movie>(ARG_MOVIE)
      }
    }
  }

  private val movieViewModel: MovieViewModel by viewModels {
    MyAndroidViewModelFactory(requireActivity().application)
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    // getArg()
    movie = args.movie
    movie?.let { movie ->
      movieViewModel.getVideos(movie.id!!)
      movieViewModel.getReviews(movie.id!!)
    }
    val videos = movieViewModel.videoList
    val reviews = movieViewModel.reviewList
    val composeView = ComposeView(requireContext())
    composeView.apply {
      setContent {
        Movie_App_ComposeTheme {
          Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
            DetailScreen(
              movie = movie!!,
              videoList = videos.value,
              reviewList = reviews.value,
              onVideoClick = { id -> openYoutubeLink(id) },
              backNav = { popBackToHome() },
              onShareClicked = { id -> shareVideoLink(id) },
            )
          }
        }
      }
    }
    return composeView
  }

  fun popBackToHome() {
    val action = DetailFragmentDirections.actionDetailFragmentToHomeFragment()
    findNavController().navigate(action)
  }

  fun openYoutubeLink(youtubeID: String?) {
    val intentApp = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$youtubeID"))
    val intentBrowser = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=$youtubeID"))
    try {
      this.startActivity(intentApp)
    } catch (ex: ActivityNotFoundException) {
      this.startActivity(intentBrowser)
    }
  }

  fun shareVideoLink(youtubeID: String?) {
    val sendIntent: Intent = Intent().apply {
      action = Intent.ACTION_SEND
      putExtra(Intent.EXTRA_TEXT, "\"http://www.youtube.com/watch?v=$youtubeID\"")
      type = "text/plain"
    }

    val shareIntent = Intent.createChooser(sendIntent, null)
    startActivity(shareIntent)
  }


}