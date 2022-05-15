package com.app.assessment.test

import android.content.Context
import android.content.SharedPreferences

/**
 * This class is used to save and retrieve values from the shared preferences.
 *
 */
class Preference(context: Context) {
    companion object {
        private const val PREFERENCE_NAME = "assessment_prefs"
        const val MOVIE_COMMENTS = "MovieComments"
        private var preference: SharedPreferences? = null
    }

    init {
        build(context)
    }

    /**
     * This method is used to build encrypted shared preferences for further use.
     */
    private fun build(context: Context): SharedPreferences {
        if (preference == null) {
            preference = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
        }
        return preference!!
    }

    fun putString(key: String, value: String) {
        preference?.edit()?.putString(key, value)?.apply()
    }

    fun getString(key: String) = preference?.getString(key, null)

}