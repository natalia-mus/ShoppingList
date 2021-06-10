package com.example.shoppinglist.model

import com.example.shoppinglist.contract.AddProductActivityContract
import com.example.shoppinglist.database.DBHelper

class AddProductActivityModel : AddProductActivityContract.AddProductActivityModel {

    private val dataBase = DBHelper.instance

    override fun saveData(name: String, quantity: String, priority: Int) {
        dataBase?.addProduct(name, quantity, priority)
    }

}