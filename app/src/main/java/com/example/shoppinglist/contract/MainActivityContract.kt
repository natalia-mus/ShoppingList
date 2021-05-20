package com.example.shoppinglist.contract

import com.example.shoppinglist.database.DBHelper
import com.example.shoppinglist.model.Product

interface MainActivityContract {

    interface MainActivityModel {
        fun fetchDataFromDB()
        fun returnData(): List<Product>
        fun deleteItemFromDB(id: Int)
    }

    interface MainActivityPresenter {
        fun fetchData()
        fun returnData(): List<Product>
        fun showData()
        fun deleteItem(id: Int)
    }

    interface MainActivityView {
        fun DBinstance(): DBHelper?
        fun showData()
        fun deleteItem(id: Int)
    }

}