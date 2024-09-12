package com.example.shoppinglist.model

import com.example.shoppinglist.Settings
import com.example.shoppinglist.contract.ThemeProvidingActivityContract
import com.example.shoppinglist.database.DBHelper

class ThemeProvidingActivityModel : ThemeProvidingActivityContract.ThemeProvidingActivityModel {

    override fun getTheme(): Theme? {
        val database = DBHelper.getInstance()
        val themeId = Settings.getThemeId()
        return database?.getTheme(themeId)
    }
}