package com.example.shoppinglist.contract

import com.example.shoppinglist.ValidationResult
import com.example.shoppinglist.constants.SavingContext

interface AddProductActivityContract {

    interface AddProductActivityModel {
        fun saveData(savingContext: SavingContext, id: Int?, name: String, quantity: String, priority: String): ValidationResult
    }

    interface AddProductActivityPresenter {
        fun saveData(savingContext: SavingContext, id: Int?, name: String, quantity: String, priority: String): ValidationResult
    }

    interface AddProductActivityView {
        fun initView()
    }

}