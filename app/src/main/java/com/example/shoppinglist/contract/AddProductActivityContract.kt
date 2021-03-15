package com.example.shoppinglist.contract

import com.example.shoppinglist.model.Product

interface AddProductActivityContract {

    interface AddProductActivityModel {
        fun saveData(product: Product)
        fun test()
    }

    interface AddProductActivityPresenter {
        fun saveData(product: Product)
        fun test()
    }

    interface AddProductActivityView {
        fun initView()
        //fun DBinstance(): DBHelper
    }

}