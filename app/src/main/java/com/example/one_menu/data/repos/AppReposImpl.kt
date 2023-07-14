package com.example.one_menu.data.repos

import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.one_menu.domain.repos.AppRepos
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppReposImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences,
): AppRepos {
    override fun getToken(): String? = sharedPreferences
        .getString("token", null)

    override fun setToken(token: String) {
        sharedPreferences.edit {
            putString("token", "Bearer $token")
        }
    }

    override fun resetToken() {
        sharedPreferences.edit {
            putString("token", null)
        }
    }
}