package com.example.shoppinglist.model

import com.example.shoppinglist.Settings
import com.example.shoppinglist.contract.ThemeProvidingActivityContract
import com.example.shoppinglist.database.DBHelper

class ThemeProvidingActivityModel : ThemeProvidingActivityContract.ThemeProvidingActivityModel {

    override fun getColorSet(): ColorSet? {
        val theme = getTheme()
        return if (theme != null) {
            DBHelper.getInstance()?.getColorSet(theme.colorSetId)
        } else null
    }

    override fun getTheme(): Theme? {
        val themeId = Settings.getThemeId()
        return DBHelper.getInstance()?.getTheme(themeId)
    }
}