package com.example.shoppinglist.presenter

import com.example.shoppinglist.ValidationResult
import com.example.shoppinglist.contract.CreateThemeActivityContract
import com.example.shoppinglist.model.CreateThemeActivityModel

class CreateThemeActivityPresenter(_view: CreateThemeActivityContract.CreateThemeActivityView) : CreateThemeActivityContract.CreateThemeActivityPresenter {

    private val view = _view
    private val model = CreateThemeActivityModel()

    init {
        view.initView()
    }

    override fun saveTheme(
        name: String,
        listBackgroundPortrait: ByteArray?,
        listBackgroundLandscape: ByteArray?,
        addProductBackgroundPortrait: ByteArray?,
        addProductBackgroundLandscape: ByteArray?
    ): ValidationResult {
        return model.saveTheme(name, listBackgroundPortrait, listBackgroundLandscape, addProductBackgroundPortrait, addProductBackgroundLandscape)
    }
}