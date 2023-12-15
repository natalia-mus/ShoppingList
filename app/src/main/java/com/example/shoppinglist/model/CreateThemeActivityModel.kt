package com.example.shoppinglist.model

import com.example.shoppinglist.ValidationResult
import com.example.shoppinglist.contract.CreateThemeActivityContract
import com.example.shoppinglist.database.DBHelper

class CreateThemeActivityModel : CreateThemeActivityContract.CreateThemeActivityModel {

    private val database = DBHelper.instance

    override fun saveTheme(
        name: String,
        listBackgroundPortrait: ByteArray?,
        listBackgroundLandscape: ByteArray?,
        addProductBackgroundPortrait: ByteArray?,
        addProductBackgroundLandscape: ByteArray?
    ): ValidationResult {
        val validationResult = validate(name, listBackgroundPortrait, listBackgroundLandscape, addProductBackgroundPortrait, addProductBackgroundLandscape)
        if (validationResult == ValidationResult.VALID) {
            database?.saveTheme(name, listBackgroundPortrait, listBackgroundLandscape, addProductBackgroundPortrait, addProductBackgroundLandscape)
        }
        return validationResult
    }

    private fun validate(
        name: String,
        listBackgroundPortrait: ByteArray?,
        listBackgroundLandscape: ByteArray?,
        addProductBackgroundPortrait: ByteArray?,
        addProductBackgroundLandscape: ByteArray?
    ): ValidationResult {
        return if (name.isEmpty()) {
            ValidationResult.EMPTY_NAME

        } else if (listBackgroundPortrait == null && listBackgroundLandscape == null && addProductBackgroundPortrait == null && addProductBackgroundLandscape == null) {
            ValidationResult.MISSING_BACKGROUNDS

        } else ValidationResult.VALID
    }
}