package com.example.shoppinglist.model

import android.util.Log
import com.example.shoppinglist.contract.AddProductActivityContract
import com.example.shoppinglist.database.DBHelper

class AddProductActivityModel : AddProductActivityContract.AddProductActivityModel {

    private val dataBase = DBHelper.instance

    override fun saveData(product: Product) {

    }

    override fun test() {
        Log.e("Model", dataBase.toString())
    }
}