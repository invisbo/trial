package com.newactivity.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PrefUtil {
    companion object {
        private const val CUSTOMER_PREFS = "SharedCustomerPrefs"
        private const val READ_LIST = "readList"

        fun addReadList(context: Context, url: String) {
            val pref = context.getSharedPreferences(
                CUSTOMER_PREFS,
                Context.MODE_PRIVATE
            )
            pref.addStringToList(READ_LIST, url)
        }

        fun removeReadList(context: Context, url: String) {
            val pref = context.getSharedPreferences(
                CUSTOMER_PREFS,
                Context.MODE_PRIVATE
            )
            pref.removeStringFromList(READ_LIST, url)
        }

        fun getReadList(context: Context): ArrayList<String> {
            val pref = context.getSharedPreferences(
                CUSTOMER_PREFS,
                Context.MODE_PRIVATE
            )
            return pref.getStringList(READ_LIST)
        }
    }
}

fun SharedPreferences.addStringToList(
    key: String,
    value: String
) {
    val list = getStringList(key)
    list.add(value)
    this.edit().putString(key, Gson().toJson(list)).apply()
}

fun SharedPreferences.removeStringFromList(
    key: String,
    value: String
) {
    val list = getStringList(key)
    list.removeAll { item -> item == value }
    this.edit().putString(key, Gson().toJson(list)).apply()
}

fun SharedPreferences.getStringList(
    key: String
): ArrayList<String> {
    val gson = Gson()
    val json = this.getString(key, null)
    val type = object : TypeToken<ArrayList<String>>() {}.type
    return gson.fromJson(json, type) ?: arrayListOf()
}
