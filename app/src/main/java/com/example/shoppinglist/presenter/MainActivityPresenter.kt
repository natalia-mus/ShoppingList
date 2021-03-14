package com.example.shoppinglist.presenter

import com.example.shoppinglist.contract.MainActivityContract
import com.example.shoppinglist.model.MainActivityModel

class MainActivityPresenter(_view: MainActivityContract.MainActivityView) :
    MainActivityContract.MainActivityPresenter {

    private val view = _view
    private val model = MainActivityModel(view.createDB())

    init {
        view.initView()
    }

    override fun fetchData() = model.fetchDataFromDB()

    override fun returnData() = model.returnData()

    override fun showData() = view.showData()

}