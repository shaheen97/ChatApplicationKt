package com.example.chatapplicationkt.utils

import android.content.Context
import android.content.SharedPreferences

class PreferenceManager {

    private var sharedPreferences: SharedPreferences? = null

    fun PreferenceManager(context: Context){
        sharedPreferences = context.getSharedPreferences(Constants.KEY_PREFERENCE_NAME, Context.MODE_PRIVATE)
    }

    fun putBoolean(key: String?, value: Boolean?) {
        val editor = sharedPreferences?.edit()
        if (value != null) {
            editor?.putBoolean(key, value)
        }
        editor?.apply()
    }

    fun getBoolean(key: String?): Boolean {
        return sharedPreferences?.getBoolean(key, false) ?: true
    }

    /*fun getBoolean(key: String?): Boolean {
        return sharedPreferences!!.getBoolean(key, false)
    }*/

    fun putString(key: String?, value: String?) {
        val editor = sharedPreferences?.edit()
        if (value != null){
            editor?.putString(key, value)
        }
        editor?.apply()
    }

    fun getString(key: String?): String {
        return sharedPreferences?.getString(key, "") ?: ""
    }

    /*fun getString(key: String?): String {
        return sharedPreferences?.getString(key, null) ?: Constants.KEY_NAME
    }*/

    fun clear() {
        val editor = sharedPreferences?.edit()
        editor?.clear()
        editor?.apply()
    }
}