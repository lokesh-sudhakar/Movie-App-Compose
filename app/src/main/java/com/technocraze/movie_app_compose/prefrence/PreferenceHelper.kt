package com.technocraze.movie_app_compose.prefrence

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager


object PreferenceHelper {

  val USER_ID = "USER_ID"
  val USER_PASSWORD = "PASSWORD"

  fun defaultPreference(context: Context): SharedPreferences {
    return PreferenceManager.getDefaultSharedPreferences(context)
  }

  fun customPreference(context: Context, name: String): SharedPreferences =
    context.getSharedPreferences(name, Context.MODE_PRIVATE)

  inline fun SharedPreferences.editMe(operation: (SharedPreferences.Editor) -> Unit) {
    val editMe = edit()
    operation(editMe)
    editMe.apply()
  }

  var SharedPreferences.pageKey
    get() = getInt("page_key",0)
    set(value) {
      editMe {
        it.putInt("page_key", value)
      }
    }

  var SharedPreferences.userId
    get() = getInt(USER_ID, 0)
    set(value) {
      editMe {
        it.putInt(USER_ID, value)
      }
    }

  var SharedPreferences.password
    get() = getString(USER_PASSWORD, "")
    set(value) {
      editMe {
        it.putString(USER_PASSWORD, value)
      }
    }

  var SharedPreferences.clearValues
    get() = { }
    set(value) {
      editMe {
        it.clear()
      }
    }
}
