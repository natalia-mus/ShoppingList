package com.example.shoppinglist.presenter

import com.example.shoppinglist.contract.ThemeProvidingActivityContract
import com.example.shoppinglist.model.Theme
import com.example.shoppinglist.model.ThemeProvidingActivityModel

class ThemeProvidingActivityPresenter(_view: ThemeProvidingActivityContract.ThemeProvidingActivityView) : ThemeProvidingActivityContract.ThemeProvidingActivityPresenter {

    private val view = _view
    private val model = ThemeProvidingActivityModel()

    init {
        view.createDBInstance()
    }

    override fun getTheme(): Theme? = model.getTheme()

    override fun onResume() {
        view.setTheme(getTheme())
    }

}