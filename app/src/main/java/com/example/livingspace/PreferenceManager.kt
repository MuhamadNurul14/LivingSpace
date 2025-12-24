package com.example.livingspace

import android.content.Context
import android.content.SharedPreferences

class PreferenceManager(context: Context) {

    private val preferences: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREF_NAME = "LivingSpacePrefs"

        // Onboarding
        private const val KEY_ONBOARDING_COMPLETED = "onboarding_completed"

        // User
        private const val KEY_USER_NAME = "user_name"
        private const val KEY_USER_EMAIL = "user_email"
        private const val KEY_USER_PHONE = "user_phone"
        private const val KEY_IS_LOGGED_IN = "is_logged_in"

        private const val KEY_USER_PHOTO = "user_photo"




        // Favorite
        private const val KEY_FAVORITES = "favorites"
    }

    /* =========================
       ONBOARDING
    ========================== */
    fun setOnboardingCompleted(completed: Boolean) {
        preferences.edit()
            .putBoolean(KEY_ONBOARDING_COMPLETED, completed)
            .apply()
    }

    fun isOnboardingCompleted(): Boolean {
        return preferences.getBoolean(KEY_ONBOARDING_COMPLETED, false)
    }

    /* =========================
       LOGIN STATUS
    ========================== */
    fun setUserLoggedIn(isLoggedIn: Boolean) {
        preferences.edit()
            .putBoolean(KEY_IS_LOGGED_IN, isLoggedIn)
            .apply()
    }

    fun isUserLoggedIn(): Boolean {
        return preferences.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    /* =========================
       USER DATA
    ========================== */
    fun setUserData(
        name: String,
        email: String,
        phone: String
    ) {
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

    fun clearUserData() {
        preferences.edit().apply {
            remove(KEY_USER_NAME)
            remove(KEY_USER_EMAIL)
            remove(KEY_USER_PHONE)
            remove(KEY_IS_LOGGED_IN)
            apply()
        }
    }

    /* =========================
       FAVORITE KOSAN
    ========================== */

    fun addFavorite(kosanId: Int) {
        val favorites = getFavorites().toMutableSet()
        favorites.add(kosanId)
        saveFavorites(favorites)
    }

    fun removeFavorite(kosanId: Int) {
        val favorites = getFavorites().toMutableSet()
        favorites.remove(kosanId)
        saveFavorites(favorites)
    }

    fun toggleFavorite(kosanId: Int): Boolean {
        val favorites = getFavorites().toMutableSet()

        val isFavoriteNow: Boolean
        if (favorites.contains(kosanId)) {
            favorites.remove(kosanId)
            isFavoriteNow = false
        } else {
            favorites.add(kosanId)
            isFavoriteNow = true
        }

        saveFavorites(favorites)
        return isFavoriteNow
    }

    fun isFavorite(kosanId: Int): Boolean {
        return getFavorites().contains(kosanId)
    }

    fun getFavorites(): Set<Int> {
        val favoritesStr =
            preferences.getStringSet(KEY_FAVORITES, emptySet()) ?: emptySet()
        return favoritesStr.mapNotNull { it.toIntOrNull() }.toSet()
    }

    private fun saveFavorites(favorites: Set<Int>) {
        preferences.edit()
            .putStringSet(
                KEY_FAVORITES,
                favorites.map { it.toString() }.toSet()
            )
            .apply()
    }


    fun setUserPhoto(url: String) {
        preferences.edit().putString(KEY_USER_PHOTO, url).apply()
    }

    fun getUserPhotoUrl(): String {
        return preferences.getString(KEY_USER_PHOTO, "") ?: ""
    }

}
