package com.example.shoppinglist.contract

import com.example.shoppinglist.ValidationResult
import com.example.shoppinglist.model.Icon

interface CreateThemeActivityContract {

    interface CreateThemeActivityModel {
        fun getDefaultDeleteIconColorValue(): Int?
        fun getDefaultProductItemBackgroundValue(): String?
        fun getDefaultProductItemTextColorValue(): Int?
        fun getDefaultAddProductTextColorValue(): Int?
        fun getDefaultAddProductLabelColorValue(): Int?
        fun getDefaultAddProductLineColorValue(): Int?

        fun saveTheme(
            name: String,
            listBackgroundImagePortrait: ByteArray?,
            listBackgroundImageLandscape: ByteArray?,
            addProductBackgroundImagePortrait: ByteArray?,
            addProductBackgroundImageLandscape: ByteArray?,
            listBackgroundColorPortrait: Int?,
            listBackgroundColorLandscape: Int?,
            addProductBackgroundColorPortrait: Int?,
            addProductBackgroundColorLandscape: Int?,
            productItemBackgroundValue: String,
            productItemTextColorValue: Int,
            deleteIconColorValue: Int,
            deleteIcon: Icon,
            boldProductName: Boolean,
            addProductTextColor: Int,
            addProductLabelColor: Int,
            addProductLineColor: Int
        )

        fun validateFirstStep(): ValidationResult

        fun validateSecondStep(
            productListPortraitBackgroundImage: ByteArray?,
            productListLandscapeBackgroundImage: ByteArray?,
            addProductPortraitBackgroundImage: ByteArray?,
            addProductLandscapeBackgroundImage: ByteArray?,
            productListPortraitBackgroundColor: Int?,
            productListLandscapeBackgroundColor: Int?,
            addProductPortraitBackgroundColor: Int?,
            addProductLandscapeBackgroundColor: Int?,
            productItemBackgroundValue: String,
            productItemTextColorValue: Int,
            deleteIconColorValue: Int,
            deleteIcon: Icon,
            boldProductName: Boolean
        ): ValidationResult

        fun validateLastStep(themeName: String): ValidationResult
    }

    interface CreateThemeActivityPresenter {
        fun getDefaultDeleteIconColorValue(): Int?
        fun getDefaultProductItemBackgroundValue(): String?
        fun getDefaultProductItemTextColorValue(): Int?
        fun getDefaultAddProductTextColorValue(): Int?
        fun getDefaultAddProductLabelColorValue(): Int?
        fun getDefaultAddProductLineColorValue(): Int?

        fun saveTheme(
            name: String,
            listBackgroundImagePortrait: ByteArray?,
            listBackgroundImageLandscape: ByteArray?,
            addProductBackgroundImagePortrait: ByteArray?,
            addProductBackgroundImageLandscape: ByteArray?,
            listBackgroundColorPortrait: Int?,
            listBackgroundColorLandscape: Int?,
            addProductBackgroundColorPortrait: Int?,
            addProductBackgroundColorLandscape: Int?,
            productItemBackgroundValue: String,
            productItemTextColorValue: Int,
            deleteIconColorValue: Int,
            deleteIcon: Icon,
            boldProductName: Boolean,
            addProductTextColor: Int,
            addProductLabelColor: Int,
            addProductLineColor: Int
        )

        fun validateFirstStep(): ValidationResult

        fun validateSecondStep(
            productListPortraitBackgroundImage: ByteArray?,
            productListLandscapeBackgroundImage: ByteArray?,
            addProductPortraitBackgroundImage: ByteArray?,
            addProductLandscapeBackgroundImage: ByteArray?,
            productListPortraitBackgroundColor: Int?,
            productListLandscapeBackgroundColor: Int?,
            addProductPortraitBackgroundColor: Int?,
            addProductLandscapeBackgroundColor: Int?,
            productItemBackgroundValue: String,
            productItemTextColorValue: Int,
            deleteIconColorValue: Int,
            deleteIcon: Icon,
            boldProductName: Boolean
        ): ValidationResult

        fun validateLastStep(themeName: String): ValidationResult
    }

    interface CreateThemeActivityView {
        fun initView()
    }
}