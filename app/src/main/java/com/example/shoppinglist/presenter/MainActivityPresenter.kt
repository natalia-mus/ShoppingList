package com.example.shoppinglist.presenter

import com.example.shoppinglist.constants.Themes
import com.example.shoppinglist.contract.MainActivityContract
import com.example.shoppinglist.model.MainActivityModel

class MainActivityPresenter(_view: MainActivityContract.MainActivityView) :
    MainActivityContract.MainActivityPresenter {

    private val view = _view
    private val model = MainActivityModel(view.createDBinstance())

    init {
        view.initSettings()
        view.setTheme(getTheme())
    }

    private fun getTheme(): Themes = model.getTheme()

    override fun fetchData() = model.fetchDataFromDB()

    override fun returnData() = model.returnData()

    override fun showData() = view.showData()

    override fun deleteItem(id: Int) = model.deleteItemFromDB(id)

}