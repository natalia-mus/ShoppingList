package com.example.shoppinglist.contract

interface AddProductActivityContract {

    interface AddProductActivityModel {
        fun saveData(name: String, quantity: String, priority: Int)
        fun updateData(id: Int, name: String, quantity: String, priority: Int)
    }

    interface AddProductActivityPresenter {
        fun saveData(name: String, quantity: String, priority: Int)
        fun updateData(id: Int, name: String, quantity: String, priority: Int)
    }

    interface AddProductActivityView {
        fun initView()
    }

}