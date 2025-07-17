package com.example.session

import android.content.Context

class SessionManager(context: Context) {
    private val prefs = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)

    fun setLogged(isLogged: Boolean) {
        prefs.edit().putBoolean("is_logged", isLogged).apply()
    }

    fun isLogged(): Boolean {
        return prefs.getBoolean("is_logged", false)
    }

    fun setUserId(userId: String) {
        prefs.edit().putString("user_id", userId).apply()
    }

    fun setUserEmail(email: String) {
        prefs.edit().putString("user_email", email).apply()
    }

    fun getUserId(): String? {
        return prefs.getString("user_id", null)
    }

    fun getUserEmail(): String? {
        return prefs.getString("user_email", null)
    }

    fun clearSession() {
        prefs.edit().clear().apply()
    }
}