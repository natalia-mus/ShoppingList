package com.example.shoppinglist.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.shoppinglist.R
import com.example.shoppinglist.contract.MainActivityContract
import com.example.shoppinglist.presenter.MainActivityPresenter

class MainActivity : AppCompatActivity(), MainActivityContract.MainActivityView {

    lateinit var presenter: MainActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = MainActivityPresenter(this)
        presenter.returnData()
    }

    override fun initView() {
        //presenter.returnData()
        Log.e("MainActivity", "initView")
    }

    override fun showData() {
        presenter.returnData()
    }

}