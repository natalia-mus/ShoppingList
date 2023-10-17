package com.example.shoppinglist.model

import com.example.shoppinglist.Settings
import com.example.shoppinglist.contract.MainActivityContract
import com.example.shoppinglist.database.DBHelper

class MainActivityModel() : MainActivityContract.MainActivityModel {

    private val database = DBHelper.instance
    private var data: List<Product>? = emptyList()

    override fun getThemeId(): Int {
        return Settings.getThemeId()
    }

    override fun fetchDataFromDB() {
        data = database?.getAllProducts()
    }

    override fun returnData(): List<Product>? {
        return data
    }

    override fun deleteItemFromDB(id: Int) {
        database?.deleteProduct(id)
    }

}