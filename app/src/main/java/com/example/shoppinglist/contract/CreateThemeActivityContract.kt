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

        fun validateFirstStep(
            listBackgroundPortrait: ByteArray?,
            listBackgroundLandscape: ByteArray?,
            addProductBackgroundPortrait: ByteArray?,
            addProductBackgroundLandscape: ByteArray?,
            listBackgroundColorPortrait: Int?,
            listBackgroundColorLandscape: Int?,
            addProductBackgroundColorPortrait: Int?,
            addProductBackgroundColorLandscape: Int?
        ): ValidationResult

        fun validateSecondStep(): ValidationResult
        fun validateLastStep(themeName: String): ValidationResult
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

        fun validateFirstStep(
            listBackgroundImagePortrait: ByteArray?,
            listBackgroundImageLandscape: ByteArray?,
            addProductBackgroundImagePortrait: ByteArray?,
            addProductBackgroundImageLandscape: ByteArray?,
            listBackgroundColorPortrait: Int?,
            listBackgroundColorLandscape: Int?,
            addProductBackgroundColorPortrait: Int?,
            addProductBackgroundColorLandscape: Int?
        ): ValidationResult

        fun validateSecondStep(): ValidationResult
        fun validateLastStep(themeName: String): ValidationResult
    }

    interface CreateThemeActivityView {
        fun initView()
    }
}