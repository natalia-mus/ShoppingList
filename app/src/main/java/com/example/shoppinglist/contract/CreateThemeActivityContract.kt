package com.example.shoppinglist.contract

import com.example.shoppinglist.ValidationResult

interface CreateThemeActivityContract {

    interface CreateThemeActivityModel {
        fun saveTheme(
            name: String,
            listBackgroundPortrait: String,
            listBackgroundLandscape: String,
            addProductBackgroundPortrait: String,
            addProductBackgroundLandscape: String
        ): ValidationResult
    }

    interface CreateThemeActivityPresenter {
        fun saveTheme(
            name: String,
            listBackgroundPortrait: String,
            listBackgroundLandscape: String,
            addProductBackgroundPortrait: String,
            addProductBackgroundLandscape: String
        ): ValidationResult
    }

    interface CreateThemeActivityView {
        fun initView()
    }
}