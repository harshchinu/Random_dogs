package com.example.doggo.data.cache

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager


private const val KEY_IMAGE_URLS = "key_image_urls"
private const val MAX_CACHE_SIZE = 20

class DogImageCache(context: Context) {
    private val sharedPreferences =
        context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
    private var imageCache = getInstance()
    private var instance: LRUCache<String, String>? = null

    init {
        loadCacheFromSharedPreferences()
    }

    private fun getInstance(): LRUCache<String, String> {
        return if (instance == null) {
            instance = LRUCache(MAX_CACHE_SIZE)
            instance!!
        } else {
            instance!!
        }
    }

    private fun loadCacheFromSharedPreferences() {
        val allEntries = sharedPreferences.all
        for ((key, value) in allEntries.entries) {
            if (key.startsWith(KEY_IMAGE_URLS)) {
                val imageUrl = value as String
                imageCache.put(key, imageUrl)
            }
        }
    }

    fun getCacheImage(): List<String> {
        val list = mutableListOf<String>()
        for (key in imageCache.getAllKeys()) {
            imageCache[key]?.let { list.add(it) }
        }
        return list.reversed()
    }

    fun saveImageUrlToCache(imageUrl: String) {
        val key = KEY_IMAGE_URLS + System.currentTimeMillis() // Unique key for each entry
        imageCache.put(key, imageUrl)
        val editor = sharedPreferences.edit()
        editor.putString(key, imageUrl)
        editor.apply()
    }

    fun clearCache() {
        imageCache.clear()
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }
}
