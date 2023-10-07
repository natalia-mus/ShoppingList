package com.example.shoppinglist.contract

import com.example.shoppinglist.constants.ThemeType

interface ThemesActivityContract {

    interface ThemesActivityModel {
        fun getActualTheme(): ThemeType
        fun setTheme(selectedTheme: ThemeType)
    }

    interface ThemesActivityPresenter {
        fun setTheme(selectedTheme: ThemeType)
    }

    interface ThemesActivityView {
        fun initView(actualTheme: ThemeType)
    }
}