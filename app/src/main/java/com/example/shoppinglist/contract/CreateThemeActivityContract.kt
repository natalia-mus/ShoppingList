package com.example.shoppinglist.contract

import com.example.shoppinglist.ValidationResult

interface CreateThemeActivityContract {

    interface CreateThemeActivityModel {
        fun saveTheme(
            name: String,
            listBackgroundImagePortrait: ByteArray?,
            listBackgroundImageLandscape: ByteArray?,
            addProductBackgroundImagePortrait: ByteArray?,
            addProductBackgroundImageLandscape: ByteArray?,
            listBackgroundColorPortrait: Int?,
            listBackgroundColorLandscape: Int?,
            addProductBackgroundColorPortrait: Int?,
            addProductBackgroundColorLandscape: Int?
        ): ValidationResult
    }

    interface CreateThemeActivityPresenter {
        fun saveTheme(
            name: String,
            listBackgroundImagePortrait: ByteArray?,
            listBackgroundImageLandscape: ByteArray?,
            addProductBackgroundImagePortrait: ByteArray?,
            addProductBackgroundImageLandscape: ByteArray?,
            listBackgroundColorPortrait: Int?,
            listBackgroundColorLandscape: Int?,
            addProductBackgroundColorPortrait: Int?,
            addProductBackgroundColorLandscape: Int?
        ): ValidationResult
    }

    interface CreateThemeActivityView {
        fun initView()
    }
}