package com.example.shoppinglist.contract

import com.example.shoppinglist.constants.SavingContext
import com.example.shoppinglist.constants.Themes

interface AddProductActivityContract {

    interface AddProductActivityModel {
        fun getTheme(): Themes
        fun createData(name: String, quantity: String, priority: Int)
        fun updateData(id: Int, name: String, quantity: String, priority: Int)
    }

    interface AddProductActivityPresenter {
        fun getTheme(): Themes
        fun saveData(savingContext: SavingContext, id: Int?, name: String, quantity: String, priority: Int)
    }

    interface AddProductActivityView {
        fun initView()
        fun setTheme(theme: Themes)
    }

}