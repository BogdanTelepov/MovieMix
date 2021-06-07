package ru.btelepov.moviemix.utils

import android.content.Context
import android.content.SharedPreferences
import ru.btelepov.moviemix.R

class SessionManager(context: Context) {

    private var prefs: SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    companion object {
        const val REQUEST_TOKEN = "request_token"
        const val SESSION_ID = "session_id"

    }

    fun saveRequestToken(token: String?) {
        val editor = prefs.edit()
        editor.putString(REQUEST_TOKEN, token)
        editor.apply()
    }

    fun saveSessionId(sessionId: String?) {
        val editor = prefs.edit()
        editor.putString(SESSION_ID, sessionId)
        editor.apply()
    }

    fun fetchSessionId(): String? {
        return prefs.getString(SESSION_ID, null)
    }

    fun fetchRequestToken(): String? {
        return prefs.getString(REQUEST_TOKEN, null)
    }

    fun clearData() {
        prefs.edit().clear().apply()
    }
}