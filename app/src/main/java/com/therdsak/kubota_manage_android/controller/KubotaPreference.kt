package com.therdsak.kubota_manage_android.controller

import android.content.Context
import android.content.SharedPreferences

class KubotaPreference (val context: Context) {

    private val PREFS_NAME = "kubota"
    val sharedPref: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)


    fun saveEmail(KEY_NAME: String, value: String?) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString(KEY_NAME, value)
        editor.commit()
    }


    fun getEmail(KEY_NAME: String): String? {
        return sharedPref.getString(KEY_NAME, null)
    }


    fun savePassword(KEY_NAME: String, value: String?) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString(KEY_NAME, value)
        editor.commit()
    }


    fun getPassword(KEY_NAME: String): String? {
        return sharedPref.getString(KEY_NAME, null)
    }

}