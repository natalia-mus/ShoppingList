package com.example.shoppinglist.model

import com.example.shoppinglist.Settings
import com.example.shoppinglist.constants.Themes
import com.example.shoppinglist.contract.MainActivityContract
import com.example.shoppinglist.database.DBHelper

class MainActivityModel(_dataBase: DBHelper?) : MainActivityContract.MainActivityModel {

    private val dataBase = _dataBase
    private var data: List<Product>? = emptyList()

    override fun getTheme(): Themes {
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