package com.example.shoppinglist.contract

import com.example.shoppinglist.constants.SavingContext

interface AddProductActivityContract {

    interface AddProductActivityModel {
        fun createData(name: String, quantity: String, priority: Int)
        fun updateData(id: Int, name: String, quantity: String, priority: Int)
    }

    interface AddProductActivityPresenter {
        fun saveData(
            savingContext: SavingContext,
            id: Int?,
            name: String,
            quantity: String,
            priority: Int
        )
    }

    interface AddProductActivityView {
        fun initView()
    }

}