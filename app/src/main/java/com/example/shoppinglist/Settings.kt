package com.example.shoppinglist

import android.content.Context
import android.content.SharedPreferences
import com.example.shoppinglist.constants.Constants

object Settings {

    var instance: SharedPreferences? = null

    private fun getSettingsName(context: Context): String {
        val name = StringBuilder()
        name.append(context.packageName)
        name.append(".")
        name.append(Constants.SETTINGS)
        return name.toString()
    }

    fun getInstance(context: Context): SharedPreferences {
        if (instance == null) {
            instance = context.applicationContext.getSharedPreferences(
                getSettingsName(context),
                Context.MODE_PRIVATE
            )
        }
        return instance as SharedPreferences
    }

    fun getThemeId(): Int {
        return if (instance != null) {
            instance!!.getInt(Constants.THEME, 0)
        } else return 0
    }

    fun setTheme(themeId: Int) {
        instance?.edit()?.putInt(Constants.THEME, themeId)?.apply()
    }
}