package com.example.shoppinglist.model

import com.example.shoppinglist.Settings
import com.example.shoppinglist.constants.Themes
import com.example.shoppinglist.contract.AddProductActivityContract
import com.example.shoppinglist.database.DBHelper

class AddProductActivityModel : AddProductActivityContract.AddProductActivityModel {

    private val dataBase = DBHelper.instance

    override fun getTheme(): Themes {
        return Settings.getTheme()
    }

    override fun saveData(name: String, quantity: String, priority: String): Boolean {
        if (validateData(name, priority)) {
            dataBase?.addProduct(name, quantity, priority.toInt())
            return true

        } else return false
    }

    override fun updateData(id: Int, name: String, quantity: String, priority: String): Boolean {
        if (validateData(name, priority)) {
            dataBase?.editProduct(id, name, quantity, priority.toInt())
            return true

        } else return false
    }

    private fun validateData(name: String, priority: String): Boolean {
        //var result = true
//        val result: Boolean
//
//        if (name.isNotEmpty() && priority.isNotEmpty()) {
//            result = true
//
//        } else if (name.isEmpty() || priority.isEmpty()) {
////            var message = ""
////
////            if (name.isEmpty()) {
////                message = resources.getString(R.string.product_name_can_not_be_empty)
////            } else if (priority.isEmpty()) {
////                message = resources.getString(R.string.priority_can_not_be_empty)
////            }
////
////            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
//            result = false
//        }

        return name.isNotEmpty() && priority.isNotEmpty()
    }

}