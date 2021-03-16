package com.example.shoppinglist.presenter

import com.example.shoppinglist.contract.AddProductActivityContract
import com.example.shoppinglist.model.AddProductActivityModel

class AddProductActivityPresenter(_view: AddProductActivityContract.AddProductActivityView) :
    AddProductActivityContract.AddProductActivityPresenter {

    private val view = _view
    private val model = AddProductActivityModel()

    init {
        view.initView()
    }

    override fun saveData(name: String, amount: String, priority: Int) {
        model.saveData(name, amount, priority)
    }

}