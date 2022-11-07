package com.example.shoppinglist.contract

import com.example.shoppinglist.constants.SavingContext
import com.example.shoppinglist.constants.Themes

interface AddProductActivityContract {

    interface AddProductActivityModel {
        fun getTheme(): Themes
        fun saveData(name: String, quantity: String, priority: String): Boolean
        fun updateData(id: Int, name: String, quantity: String, priority: String): Boolean
    }

    interface AddProductActivityPresenter {
        fun getTheme(): Themes
        fun saveData(savingContext: SavingContext, id: Int?, name: String, quantity: String, priority: String)
    }

    interface AddProductActivityView {
        fun initView()
        fun setTheme(theme: Themes)
    }

}