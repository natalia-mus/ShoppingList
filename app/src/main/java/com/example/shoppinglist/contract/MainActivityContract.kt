package com.example.shoppinglist.contract

import com.example.shoppinglist.constants.ThemeType
import com.example.shoppinglist.database.DBHelper
import com.example.shoppinglist.model.Product

interface MainActivityContract {

    interface MainActivityModel {
        fun getTheme(): ThemeType
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
        fun initSettings()
        fun setTheme(themeType: ThemeType)
        fun createDBinstance(): DBHelper?
        fun showData()
        fun deleteItem(id: Int)
    }

}