package com.example.shoppinglist.model

import com.example.shoppinglist.contract.MainActivityContract
import com.example.shoppinglist.core.App
import com.example.shoppinglist.database.DBHelper

class MainActivityModel(_dataBase: DBHelper) : MainActivityContract.MainActivityModel {

    private val dataBase = _dataBase
    private var data: List<Product> = emptyList()

    override fun fetchDataFromDB() {
        /*dataBase = DBHelper(App.context)
        data = dataBase.getAllProducts()*/
        dataBase.test()
    }

    override fun returnData() = data

}