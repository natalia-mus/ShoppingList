package com.example.shoppinglist.contract

import com.example.shoppinglist.constants.ThemeType

interface ThemesActivityContract {

    interface ThemesActivityModel {
        fun getActualTheme(): ThemeType
        fun setTheme(selectedThemeType: ThemeType)
    }

    interface ThemesActivityPresenter {
        fun setTheme(selectedThemeType: ThemeType)
    }

    interface ThemesActivityView {
        fun initView(actualThemeType: ThemeType)
    }
}