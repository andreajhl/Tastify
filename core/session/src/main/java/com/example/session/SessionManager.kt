package com.example.session

import android.content.Context

class SessionManager(context: Context) {
    private val prefs = context.getSharedPreferences("user", Context.MODE_PRIVATE)

    fun setLogged(value: Boolean) {
        prefs.edit().putBoolean("is_logged", value).apply()
    }

    fun isLogged(): Boolean = prefs.getBoolean("is_logged", false)

    fun clearSession() {
        prefs.edit().clear().apply()
    }
}