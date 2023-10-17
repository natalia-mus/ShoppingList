package com.example.shoppinglist.presenter

import com.example.shoppinglist.contract.ThemesActivityContract
import com.example.shoppinglist.model.ThemesActivityModel

class ThemesActivityPresenter(_view: ThemesActivityContract.ThemesActivityView) :
    ThemesActivityContract.ThemesActivityPresenter {

    private val view = _view
    private val model = ThemesActivityModel()

    private val allThemes = model.getAllThemes()
    private val actualThemeId = model.getActualThemeId()

    init {
        view.initView(allThemes, actualThemeId)
    }

    override fun setTheme(selectedThemeId: Int) {
        model.setTheme(selectedThemeId)
    }
}