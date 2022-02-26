package com.example.shoppinglist.presenter

import com.example.shoppinglist.constants.SavingContext
import com.example.shoppinglist.contract.AddProductActivityContract
import com.example.shoppinglist.model.AddProductActivityModel

class AddProductActivityPresenter(_view: AddProductActivityContract.AddProductActivityView) :
    AddProductActivityContract.AddProductActivityPresenter {

    private val view = _view
    private val model = AddProductActivityModel()

    init {
        view.initView()
    }

    override fun saveData(
        savingContext: SavingContext,
        id: Int?,
        name: String,
        quantity: String,
        priority: Int
    ) {
        if (savingContext == SavingContext.CREATE)  model.createData(name, quantity, priority)
        else if (savingContext == SavingContext.EDIT)   model.updateData(id!!, name, quantity, priority)        // product id is never null in this case so we can use !! operator
    }

}