package com.example.shoppinglist.presenter

import com.example.shoppinglist.contract.AddProductActivityContract
import com.example.shoppinglist.model.AddProductActivityModel
import com.example.shoppinglist.model.Product

class AddProductActivityPresenter(_view: AddProductActivityContract.AddProductActivityView) :
    AddProductActivityContract.AddProductActivityPresenter {

    private val view = _view
    private val model = AddProductActivityModel()

    init {
        view.initView()
    }

    override fun saveData(product: Product) {

    }

    override fun test() {
        model.test()
    }
}