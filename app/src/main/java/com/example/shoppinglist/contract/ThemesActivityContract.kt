package com.example.shoppinglist.contract

import com.example.shoppinglist.model.Theme

interface ThemesActivityContract {

    interface ThemesActivityModel {
        fun getActualThemeId(): Int
        fun getAllThemes(): ArrayList<Theme>?
        fun setTheme(themeId: Int)
    }

    interface ThemesActivityPresenter {
        fun setTheme(selectedThemeId: Int)
    }

    interface ThemesActivityView {
        fun initView(themes: ArrayList<Theme>?, actualThemeId: Int)
    }
}