package com.example.shoppinglist.model

import com.example.shoppinglist.ValidationResult
import com.example.shoppinglist.contract.CreateThemeActivityContract
import com.example.shoppinglist.database.DBHelper

class CreateThemeActivityModel : CreateThemeActivityContract.CreateThemeActivityModel {

    private val database = DBHelper.getInstance()

    override fun saveTheme(
        name: String,
        listBackgroundImagePortrait: ByteArray?,
        listBackgroundImageLandscape: ByteArray?,
        addProductBackgroundImagePortrait: ByteArray?,
        addProductBackgroundImageLandscape: ByteArray?,
        listBackgroundColorPortrait: Int?,
        listBackgroundColorLandscape: Int?,
        addProductBackgroundColorPortrait: Int?,
        addProductBackgroundColorLandscape: Int?
    ): ValidationResult {
        database?.saveTheme(
            name, false, listBackgroundImagePortrait, listBackgroundImageLandscape, addProductBackgroundImagePortrait, addProductBackgroundImageLandscape,
            listBackgroundColorPortrait, listBackgroundColorLandscape, addProductBackgroundColorPortrait, addProductBackgroundColorLandscape
        )

        return ValidationResult.VALID
    }

    override fun validateFirstStep(): ValidationResult {
        return ValidationResult.VALID       // first step is always valid
    }

    override fun validateSecondStep(
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
    ): ValidationResult {
        return ValidationResult.VALID   // todo
    }

    override fun validateLastStep(themeName: String): ValidationResult {
        return if (themeName.isEmpty()) {
            ValidationResult.EMPTY_NAME
        } else return ValidationResult.VALID
    }
}