package com.example.shoppinglist

import android.content.Context
import android.content.SharedPreferences
import com.example.shoppinglist.constants.Constants
import com.example.shoppinglist.constants.ThemeType

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

    fun getTheme(): ThemeType {
        var themeType = ThemeType.GROCERY

        when (instance?.getString(Constants.THEME, ThemeType.GROCERY.name)) {
            ThemeType.GROCERY.name -> themeType = ThemeType.GROCERY
            ThemeType.MARKETPLACE.name -> themeType = ThemeType.MARKETPLACE
            ThemeType.FASHION.name -> themeType = ThemeType.FASHION
            ThemeType.CHRISTMAS.name -> themeType = ThemeType.CHRISTMAS
        }
        return themeType
    }

    fun setTheme(themeType: ThemeType) {
        instance?.edit()?.putString(Constants.THEME, themeType.name)?.apply()
    }
}