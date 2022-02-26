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

    override fun saveData(name: String, quantity: String, priority: Int) {
        model.saveData(name, quantity, priority)
    }

    override fun updateData(id: Int, name: String, quantity: String, priority: Int) {
        model.updateData(id, name, quantity, priority)
    }

}