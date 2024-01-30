package com.example.shoppinglist.presenter

import com.example.shoppinglist.constants.Constants
import com.example.shoppinglist.contract.ThemesActivityContract
import com.example.shoppinglist.model.ThemesActivityModel

class ThemesActivityPresenter(_view: ThemesActivityContract.ThemesActivityView) :
    ThemesActivityContract.ThemesActivityPresenter {

    private val view = _view
    private val model = ThemesActivityModel()

    private val allThemes = model.getAllThemes()

    init {
        view.initView(allThemes, model.getActualThemeId())
    }

    override fun deleteTheme(themeId: Int) {
        if (themeId == model.getActualThemeId()) {
            setTheme(Constants.THEME_GROCERY_ID)
        }
        model.deleteTheme(themeId)
        onViewResume()
        view.refreshSelection(model.getActualThemeId())
    }

    override fun onViewResume() {
        view.refreshThemesList(model.getAllThemes())
    }

    override fun setTheme(selectedThemeId: Int) {
        model.setTheme(selectedThemeId)
    }
}