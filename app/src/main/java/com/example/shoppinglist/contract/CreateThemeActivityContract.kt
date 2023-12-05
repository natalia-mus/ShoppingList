package com.example.shoppinglist.contract

interface CreateThemeActivityContract {

    interface CreateThemeActivityModel {
        fun saveTheme(
            name: String,
            listBackgroundPortrait: String,
            listBackgroundLandscape: String,
            addProductBackgroundPortrait: String,
            addProductBackgroundLandscape: String
        )
    }

    interface CreateThemeActivityPresenter {
        fun saveTheme(
            name: String,
            listBackgroundPortrait: String,
            listBackgroundLandscape: String,
            addProductBackgroundPortrait: String,
            addProductBackgroundLandscape: String
        )
    }

    interface CreateThemeActivityView {
        fun initView()
    }
}