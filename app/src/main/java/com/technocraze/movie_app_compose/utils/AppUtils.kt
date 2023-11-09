package com.technocraze.movie_app_compose.utils

import android.content.Context
import android.content.Intent
import android.net.Uri

object AppUtils {

  fun String.shareTextIntent(): Intent {
    val string = this
    val intent = Intent().apply {
      action = Intent.ACTION_SEND
      putExtra(Intent.EXTRA_TEXT, string)
      type = "text/plain"
    }
    return Intent.createChooser(intent, null)
  }

  fun getYoutubeString(youtubeId: String?): String {
    return "http://www.youtube.com/watch?v=$youtubeId"
  }

  fun getYoutubeThumbnailUrl(videoKey: String?): String{
    return "https://i.ytimg.com/vi/${videoKey}/hqdefault.jpg"
  }

  fun isAppInstalled(context: Context, packageName: String): Boolean {
    val mIntent: Intent? = context.packageManager.getLaunchIntentForPackage(packageName)
    return mIntent != null
  }

  fun launchYoutubeVideoIntent(
    context: Context,
    youtubeID: String?
  ): Intent {
    return if (isAppInstalled(context, Constants.YOUTUBE_PACKAGE_NAME)) {
      Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$youtubeID"))
    } else {
      Intent(Intent.ACTION_VIEW, Uri.parse(getYoutubeString(youtubeID)))
    }
  }


}