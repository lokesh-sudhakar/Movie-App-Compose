package com.technocraze.movie_app_compose.prefrence

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager


object PreferenceHelper {

  private const val PAGE_KEY = "page_key"

  fun defaultPreference(context: Context): SharedPreferences {
    return PreferenceManager.getDefaultSharedPreferences(context)
  }

  inline fun SharedPreferences.editMe(operation: (SharedPreferences.Editor) -> Unit) {
    val editMe = edit()
    operation(editMe)
    editMe.apply()
  }

  var SharedPreferences.pageKey
    get() = getInt(PAGE_KEY, 0)
    set(value) {
      editMe {
        it.putInt(PAGE_KEY, value)
      }
    }

}
