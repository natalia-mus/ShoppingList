package com.example.shoppinglist.contract

import com.example.shoppinglist.constants.Themes

interface ThemesActivityContract {

    interface ThemesActivityModel {
        fun getActualTheme(): Themes
        fun setTheme(selectedTheme: Themes)
    }

    interface ThemesActivityPresenter {
        fun setTheme(selectedTheme: Themes)
    }

    interface ThemesActivityView {
        fun initView(actualTheme: Themes)
    }
}