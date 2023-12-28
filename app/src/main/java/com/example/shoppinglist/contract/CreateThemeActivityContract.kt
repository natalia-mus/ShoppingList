package com.example.shoppinglist.contract

import com.example.shoppinglist.ValidationResult

interface CreateThemeActivityContract {

    interface CreateThemeActivityModel {
        fun saveTheme(
            name: String,
            listBackgroundPortrait: ByteArray?,
            listBackgroundLandscape: ByteArray?,
            addProductBackgroundPortrait: ByteArray?,
            addProductBackgroundLandscape: ByteArray?
        ): ValidationResult
    }

    interface CreateThemeActivityPresenter {
        fun saveTheme(
            name: String,
            listBackgroundPortrait: ByteArray?,
            listBackgroundLandscape: ByteArray?,
            addProductBackgroundPortrait: ByteArray?,
            addProductBackgroundLandscape: ByteArray?
        ): ValidationResult
    }

    interface CreateThemeActivityView {
        fun initView()
    }
}