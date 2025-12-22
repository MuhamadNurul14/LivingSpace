package com.example.livingspace

import android.content.Context
import android.content.SharedPreferences

class PreferenceManager(context: Context) {

    private val preferences: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREF_NAME = "LivingSpacePrefs"
        private const val KEY_ONBOARDING_COMPLETED = "onboarding_completed"
        private const val KEY_USER_NAME = "user_name"
        private const val KEY_USER_EMAIL = "user_email"
        private const val KEY_USER_PHONE = "user_phone"
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
        private const val KEY_FAVORITES = "favorites"
    }

    fun setOnboardingCompleted(completed: Boolean) {
        preferences.edit().putBoolean(KEY_ONBOARDING_COMPLETED, completed).apply()
    }

    fun isOnboardingCompleted(): Boolean {
        return preferences.getBoolean(KEY_ONBOARDING_COMPLETED, false)
    }

    fun setUserLoggedIn(isLoggedIn: Boolean) {
        preferences.edit().putBoolean(KEY_IS_LOGGED_IN, isLoggedIn).apply()
    }

    fun isUserLoggedIn(): Boolean {
        return preferences.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun setUserData(name: String, email: String, phone: String) {
        preferences.edit().apply {
            putString(KEY_USER_NAME, name)
            putString(KEY_USER_EMAIL, email)
            putString(KEY_USER_PHONE, phone)
            apply()
        }
    }

    fun getUserName(): String {
        return preferences.getString(KEY_USER_NAME, "User") ?: "User"
    }

    fun getUserEmail(): String {
        return preferences.getString(KEY_USER_EMAIL, "") ?: ""
    }

    fun getUserPhone(): String {
        return preferences.getString(KEY_USER_PHONE, "") ?: ""
    }

    fun addFavorite(kosanId: Int) {
        val favorites = getFavorites().toMutableSet()
        favorites.add(kosanId)
        preferences.edit().putStringSet(KEY_FAVORITES, favorites.map { it.toString() }.toSet()).apply()
    }

    fun removeFavorite(kosanId: Int) {
        val favorites = getFavorites().toMutableSet()
        favorites.remove(kosanId)
        preferences.edit().putStringSet(KEY_FAVORITES, favorites.map { it.toString() }.toSet()).apply()
    }

    fun isFavorite(kosanId: Int): Boolean {
        return getFavorites().contains(kosanId)
    }

    fun getFavorites(): Set<Int> {
        val favoritesStr = preferences.getStringSet(KEY_FAVORITES, emptySet()) ?: emptySet()
        return favoritesStr.mapNotNull { it.toIntOrNull() }.toSet()
    }

    fun clearUserData() {
        preferences.edit().apply {
            remove(KEY_USER_NAME)
            remove(KEY_USER_EMAIL)
            remove(KEY_USER_PHONE)
            remove(KEY_IS_LOGGED_IN)
            apply()
        }
    }
}
