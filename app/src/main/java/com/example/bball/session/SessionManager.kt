package com.example.bball.session

import android.content.Context
import androidx.core.content.edit
import com.example.bball.models.User

class SessionManager(context: Context) {

    private val prefs =
        context.getSharedPreferences("user_session", Context.MODE_PRIVATE)

    fun saveUser(user: User) {
        prefs.edit {
            putBoolean("is_logged", true)
                .putString("username", user.username)
                .putString("joueur", user.joueur)
        }
    }

    fun getUser(): User? {
        if (!prefs.getBoolean("is_logged", false)) return null

        return User(
            username = prefs.getString("username", "") ?: "",
            joueur = prefs.getString("joueur", "") ?: ""
        )
    }

    fun isLogged(): Boolean =
        prefs.getBoolean("is_logged", false)

    fun logout() {
        prefs.edit { clear() }
    }
}