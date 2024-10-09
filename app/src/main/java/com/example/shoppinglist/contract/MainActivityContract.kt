package com.example.shoppinglist.contract

import com.example.shoppinglist.model.Product

interface MainActivityContract {

    interface MainActivityModel {
        fun fetchDataFromDB()
        fun returnData(): List<Product>?
        fun deleteItemFromDB(id: Int)
    }

    interface MainActivityPresenter {
        fun showData()
        fun fetchData()
        fun returnData(): List<Product>?
        fun deleteItem(id: Int)
    }

    interface MainActivityView {
        //fun initSettings()
        fun showData()
        fun deleteItem(id: Int)
    }

}