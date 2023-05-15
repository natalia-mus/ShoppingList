package com.example.shoppinglist

import android.content.Context
import android.content.SharedPreferences
import com.example.shoppinglist.constants.Constants
import com.example.shoppinglist.constants.Theme

object Settings {

    private var instance: SharedPreferences? = null

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

    fun getTheme(): Theme {
        var theme = Theme.GROCERY

        when (instance?.getString(Constants.THEME, Theme.GROCERY.name)) {
            Theme.GROCERY.name -> theme = Theme.GROCERY
            Theme.MARKETPLACE.name -> theme = Theme.MARKETPLACE
            Theme.FASHION.name -> theme = Theme.FASHION
            Theme.CHRISTMAS.name -> theme = Theme.CHRISTMAS
        }
        return theme
    }

    fun setTheme(theme: Theme) {
        instance?.edit()?.putString(Constants.THEME, theme.name)?.apply()
    }
}