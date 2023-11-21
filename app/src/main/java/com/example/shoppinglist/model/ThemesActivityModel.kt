package com.example.shoppinglist.model

import com.example.shoppinglist.Settings
import com.example.shoppinglist.contract.ThemesActivityContract
import com.example.shoppinglist.database.DBHelper

class ThemesActivityModel : ThemesActivityContract.ThemesActivityModel {

    override fun getActualThemeId(): Int {
        return Settings.getThemeId()
    }

    override fun getAllThemes(): ArrayList<Theme> {
        return DBHelper.instance?.getAllThemes() as ArrayList<Theme>
    }

    override fun setTheme(themeId: Int) {
        Settings.setTheme(themeId)
    }
}