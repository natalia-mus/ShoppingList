package com.example.shoppinglist.model

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
    ) {
        database?.saveTheme(name, listBackgroundPortrait, listBackgroundLandscape, addProductBackgroundPortrait, addProductBackgroundLandscape)
    }
}