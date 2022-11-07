package com.example.shoppinglist.presenter

import com.example.shoppinglist.ValidationResult
import com.example.shoppinglist.constants.SavingContext
import com.example.shoppinglist.constants.Themes
import com.example.shoppinglist.contract.AddProductActivityContract
import com.example.shoppinglist.model.AddProductActivityModel

class AddProductActivityPresenter(_view: AddProductActivityContract.AddProductActivityView) :
    AddProductActivityContract.AddProductActivityPresenter {

    private val view = _view
    private val model = AddProductActivityModel()

    init {
        view.setTheme(getTheme())
        view.initView()
    }

    override fun getTheme(): Themes {
        return model.getTheme()
    }

    override fun saveData(savingContext: SavingContext, id: Int?, name: String, quantity: String, priority: String): ValidationResult {
        return model.saveData(savingContext, id, name, quantity, priority)
    }

}