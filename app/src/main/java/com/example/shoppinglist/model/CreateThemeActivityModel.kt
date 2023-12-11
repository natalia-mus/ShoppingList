package com.example.shoppinglist.model

import com.example.shoppinglist.ValidationResult
import com.example.shoppinglist.contract.CreateThemeActivityContract
import com.example.shoppinglist.database.DBHelper

class CreateThemeActivityModel : CreateThemeActivityContract.CreateThemeActivityModel {

    private val database = DBHelper.instance

    override fun saveTheme(
        name: String,
        listBackgroundPortrait: String,
        listBackgroundLandscape: String,
        addProductBackgroundPortrait: String,
        addProductBackgroundLandscape: String
    ): ValidationResult {
        val validationResult = validate(name, listBackgroundPortrait, listBackgroundLandscape, addProductBackgroundPortrait, addProductBackgroundLandscape)
        if (validationResult == ValidationResult.VALID) {
            database?.saveTheme(name, listBackgroundPortrait, listBackgroundLandscape, addProductBackgroundPortrait, addProductBackgroundLandscape)
        }
        return validationResult
    }

    private fun validate(
        name: String,
        listBackgroundPortrait: String,
        listBackgroundLandscape: String,
        addProductBackgroundPortrait: String,
        addProductBackgroundLandscape: String
    ): ValidationResult {
        return if (name.isEmpty()) {
            ValidationResult.EMPTY_NAME

        } else if (listBackgroundPortrait.isEmpty() && listBackgroundLandscape.isEmpty() && addProductBackgroundPortrait.isEmpty() && addProductBackgroundLandscape.isEmpty()) {
            ValidationResult.MISSING_BACKGROUNDS

        } else ValidationResult.VALID
    }
}