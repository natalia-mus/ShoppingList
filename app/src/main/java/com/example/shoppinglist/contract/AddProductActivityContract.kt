package com.example.shoppinglist.contract

interface AddProductActivityContract {

    interface AddProductActivityModel {
        fun saveData(name: String, amount: String, priority: Int)
    }

    interface AddProductActivityPresenter {
        fun saveData(name: String, amount: String, priority: Int)
    }

    interface AddProductActivityView {
        fun initView()
    }

}