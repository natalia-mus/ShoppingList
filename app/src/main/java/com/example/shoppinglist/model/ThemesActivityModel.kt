package com.example.shoppinglist.model

import com.example.shoppinglist.Settings
import com.example.shoppinglist.constants.Themes
import com.example.shoppinglist.contract.ThemesActivityContract

class ThemesActivityModel : ThemesActivityContract.ThemesActivityModel {

    override fun getActualTheme(): Themes {
        return Settings.getTheme()
    }

    override fun setTheme(selectedTheme: Themes) {
        Settings.setTheme(selectedTheme)
    }
}