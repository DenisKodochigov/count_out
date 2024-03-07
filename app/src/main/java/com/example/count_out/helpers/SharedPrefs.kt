//package com.example.count_out.helpers
//
//import android.content.Context
//import android.content.SharedPreferences
//
//const val PREFS_NAME = "MemoAppSharedPrefs"
//const val LEVEL = "StoredLevel"
//class SharedPrefs(context: Context) {
//
//    private val sharedPreferences: SharedPreferences by lazy {
//        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE) }
//    fun storeLevel(level: Level) {
//        sharedPreferences.edit().putInt(LEVEL, level.numberOfCards).apply() }
//    fun getStoredLevel() = sharedPreferences.getInt(LEVEL, Level.BEGINNER.numberOfCards)
//}
//
//enum class Level(val numberOfCards: Int) {
//    BEGINNER(2),
//    INTERMEDIATE(3),
//    ADVANCED(4),
//    EXPERT(5),
//    NONE(0);
//
//    companion object {
//        fun getLevel(num: Int): Level? =
//            Level.values().find { it.numberOfCards == num }
//    }
//}