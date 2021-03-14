package com.example.shoppinglist.presenter

import android.util.Log
import com.example.shoppinglist.contract.MainActivityContract
import com.example.shoppinglist.database.DBHelper
import com.example.shoppinglist.model.MainActivityModel
import com.example.shoppinglist.model.Product

class MainActivityPresenter(_view: MainActivityContract.MainActivityView) :
    MainActivityContract.MainActivityPresenter {

    private val view = _view
    private val model = MainActivityModel(view.createDB())

    init {
        view.initView()
    }

    //override fun fetchData() = model.fetchDataFromDB()

    //override fun returnData() = model.returnData()

    override fun returnData(): List<Product> {
        model.fetchDataFromDB()
        Log.e("Presenter", "returnData()")
        return model.returnData()
    }

    override fun showData() = view.showData()

}