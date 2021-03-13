package com.example.shoppinglist.model

import com.example.shoppinglist.contract.MainActivityContract
import com.example.shoppinglist.core.App
import com.example.shoppinglist.database.DBHelper

class MainActivityModel() : MainActivityContract.MainActivityModel {

    lateinit var dataBase: DBHelper
    private var data: List<Product> = emptyList()

    /*init {
        dataBase = DBHelper(App.context)
    }*/

    override fun fetchDataFromDB() {
        dataBase = DBHelper(App.context)
        data = dataBase.getAllProducts()
    }

    override fun returnData() = data

}