package com.example.shoppinglist

import android.content.Context
import android.content.SharedPreferences
import com.example.shoppinglist.constants.Constants
import com.example.shoppinglist.constants.Themes

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

    fun getTheme(): Themes {
        var theme = Themes.GROCERY

        when (instance?.getString(Constants.THEME, Themes.GROCERY.name)) {
            Themes.GROCERY.name -> theme = Themes.GROCERY
            Themes.MARKETPLACE.name -> theme = Themes.MARKETPLACE
            Themes.FASHION.name -> theme = Themes.FASHION
            Themes.CHRISTMAS.name -> theme = Themes.CHRISTMAS
        }
        return theme
    }

    fun setTheme(theme: Themes) {
        instance?.edit()?.putString(Constants.THEME, theme.name)?.apply()
    }
}