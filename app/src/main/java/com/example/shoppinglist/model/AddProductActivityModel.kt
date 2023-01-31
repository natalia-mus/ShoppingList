package com.example.shoppinglist.model

import com.example.shoppinglist.Settings
import com.example.shoppinglist.ValidationResult
import com.example.shoppinglist.constants.SavingContext
import com.example.shoppinglist.constants.Theme
import com.example.shoppinglist.contract.AddProductActivityContract
import com.example.shoppinglist.database.DBHelper

class AddProductActivityModel : AddProductActivityContract.AddProductActivityModel {

    private val dataBase = DBHelper.instance

    override fun getTheme(): Theme {
        return Settings.getTheme()
    }

    override fun saveData(savingContext: SavingContext, id: Int?, name: String, quantity: String, priority: String): ValidationResult {
        val validationResult = validateData(name, priority)

        if (validationResult == ValidationResult.VALID) {
            if (savingContext == SavingContext.CREATE)  dataBase?.addProduct(name, quantity, priority.toInt())
            else dataBase?.editProduct(id!!, name, quantity, priority.toInt())
        }
        return validationResult
    }

    private fun validateData(name: String, priority: String): ValidationResult {
        return when {
            name.isEmpty() -> {
                ValidationResult.EMPTY_NAME

            }
            priority.isEmpty() -> {
                ValidationResult.EMPTY_PRIORITY

            }
            else -> ValidationResult.VALID
        }
    }

}