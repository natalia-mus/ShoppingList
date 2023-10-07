package com.example.shoppinglist.presenter

import com.example.shoppinglist.constants.ThemeType
import com.example.shoppinglist.contract.ThemesActivityContract
import com.example.shoppinglist.model.ThemesActivityModel

class ThemesActivityPresenter(_view: ThemesActivityContract.ThemesActivityView) :
    ThemesActivityContract.ThemesActivityPresenter {

    private val view = _view
    private val model = ThemesActivityModel()

    private val actualTheme = model.getActualTheme()

    init {
        view.initView(actualTheme)
    }

    override fun setTheme(selectedThemeType: ThemeType) {
        model.setTheme(selectedThemeType)
    }
}