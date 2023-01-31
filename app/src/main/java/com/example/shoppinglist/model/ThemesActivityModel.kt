package com.example.shoppinglist.model

import com.example.shoppinglist.Settings
import com.example.shoppinglist.constants.Theme
import com.example.shoppinglist.contract.ThemesActivityContract

class ThemesActivityModel : ThemesActivityContract.ThemesActivityModel {

    override fun getActualTheme(): Theme {
        return Settings.getTheme()
    }

    override fun setTheme(selectedTheme: Theme) {
        Settings.setTheme(selectedTheme)
    }
}