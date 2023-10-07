package com.example.shoppinglist.model

import com.example.shoppinglist.Settings
import com.example.shoppinglist.constants.ThemeType
import com.example.shoppinglist.contract.MainActivityContract
import com.example.shoppinglist.database.DBHelper

class MainActivityModel() : MainActivityContract.MainActivityModel {

    private val dataBase = DBHelper.instance
    private var data: List<Product>? = emptyList()

    override fun getTheme(): ThemeType {
        return Settings.getTheme()
    }

    override fun fetchDataFromDB() {
        data = dataBase?.getAllProducts()
    }

    override fun returnData(): List<Product>? {
        return data
    }

    override fun deleteItemFromDB(id: Int) {
        dataBase?.deleteProduct(id)
    }

}