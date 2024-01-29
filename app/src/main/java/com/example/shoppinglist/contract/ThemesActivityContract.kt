package com.example.shoppinglist.contract

import com.example.shoppinglist.model.Theme

interface ThemesActivityContract {

    interface ThemesActivityModel {
        fun deleteTheme(themeId: Int)
        fun getActualThemeId(): Int
        fun getAllThemes(): ArrayList<Theme>?
        fun setTheme(themeId: Int)
    }

    interface ThemesActivityPresenter {
        fun deleteTheme(themeId: Int)
        fun onViewResume()
        fun setTheme(selectedThemeId: Int)
    }

    interface ThemesActivityView {
        fun initView(themes: ArrayList<Theme>?, actualThemeId: Int)
        fun refreshSelection(actualThemeId: Int)
        fun refreshThemesList(themes: ArrayList<Theme>?)
    }
}