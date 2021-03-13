package com.example.shoppinglist.presenter

import com.example.shoppinglist.contract.MainActivityContract
import com.example.shoppinglist.model.MainActivityModel
import com.example.shoppinglist.model.Product

class MainActivityPresenter(_view: MainActivityContract.MainActivityView) :
    MainActivityContract.MainActivityPresenter {

    private val view = _view
    private val model = MainActivityModel()

    init {
        view.initView()
    }

    //override fun fetchData() = model.fetchDataFromDB()

    //override fun returnData() = model.returnData()

    override fun returnData(): List<Product> {
        model.fetchDataFromDB()
        return model.returnData()
    }

    override fun showData() = view.showData()

}