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
        val validationResult = validate(
            name, listBackgroundImagePortrait, listBackgroundImageLandscape, addProductBackgroundImagePortrait, addProductBackgroundImageLandscape,
            listBackgroundColorPortrait, listBackgroundColorLandscape, addProductBackgroundColorPortrait, addProductBackgroundColorLandscape
        )
        if (validationResult == ValidationResult.VALID) {
            database?.saveTheme(
                name, false, listBackgroundImagePortrait, listBackgroundImageLandscape, addProductBackgroundImagePortrait, addProductBackgroundImageLandscape,
                listBackgroundColorPortrait, listBackgroundColorLandscape, addProductBackgroundColorPortrait, addProductBackgroundColorLandscape
            )
        }
        return validationResult
    }

    override fun validateFirstStep(
        listBackgroundPortrait: ByteArray?,
        listBackgroundLandscape: ByteArray?,
        addProductBackgroundPortrait: ByteArray?,
        addProductBackgroundLandscape: ByteArray?,
        listBackgroundColorPortrait: Int?,
        listBackgroundColorLandscape: Int?,
        addProductBackgroundColorPortrait: Int?,
        addProductBackgroundColorLandscape: Int?
    ): ValidationResult {
        return if (listBackgroundPortrait == null && listBackgroundLandscape == null && addProductBackgroundPortrait == null && addProductBackgroundLandscape == null
            && listBackgroundColorPortrait == null && listBackgroundColorLandscape == null && addProductBackgroundColorPortrait == null && addProductBackgroundColorLandscape == null
        ) {
            ValidationResult.MISSING_BACKGROUNDS

        } else ValidationResult.VALID
    }

    override fun validateSecondStep(): ValidationResult {
        return ValidationResult.VALID
    }

    override fun validateLastStep(themeName: String): ValidationResult {
        return if (themeName.isEmpty()) {
            ValidationResult.EMPTY_NAME
        } else return ValidationResult.VALID
    }

    private fun validate(
        name: String,
        listBackgroundPortrait: ByteArray?,
        listBackgroundLandscape: ByteArray?,
        addProductBackgroundPortrait: ByteArray?,
        addProductBackgroundLandscape: ByteArray?,
        listBackgroundColorPortrait: Int?,
        listBackgroundColorLandscape: Int?,
        addProductBackgroundColorPortrait: Int?,
        addProductBackgroundColorLandscape: Int?
    ): ValidationResult {
        return if (name.isEmpty()) {
            ValidationResult.EMPTY_NAME

        } else if (listBackgroundPortrait == null && listBackgroundLandscape == null && addProductBackgroundPortrait == null && addProductBackgroundLandscape == null
            && listBackgroundColorPortrait == null && listBackgroundColorLandscape == null && addProductBackgroundColorPortrait == null && addProductBackgroundColorLandscape == null
        ) {
            ValidationResult.MISSING_BACKGROUNDS

        } else ValidationResult.VALID
    }
}