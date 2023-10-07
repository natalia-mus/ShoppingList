package com.example.shoppinglist.model

import com.example.shoppinglist.Settings
import com.example.shoppinglist.constants.ThemeType
import com.example.shoppinglist.contract.ThemesActivityContract

class ThemesActivityModel : ThemesActivityContract.ThemesActivityModel {

    override fun getActualTheme(): ThemeType {
        return Settings.getTheme()
    }

    override fun setTheme(selectedThemeType: ThemeType) {
        Settings.setTheme(selectedThemeType)
    }
}