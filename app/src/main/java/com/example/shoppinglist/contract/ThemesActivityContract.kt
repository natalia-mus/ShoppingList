package com.example.shoppinglist.contract

import com.example.shoppinglist.constants.Theme

interface ThemesActivityContract {

    interface ThemesActivityModel {
        fun getActualTheme(): Theme
        fun setTheme(selectedTheme: Theme)
    }

    interface ThemesActivityPresenter {
        fun setTheme(selectedTheme: Theme)
    }

    interface ThemesActivityView {
        fun initView(actualTheme: Theme)
    }
}