package com.example.shoppinglist.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.shoppinglist.contract.ThemeProvidingActivityContract
import com.example.shoppinglist.database.DBHelper
import com.example.shoppinglist.model.Theme
import com.example.shoppinglist.presenter.ThemeProvidingActivityPresenter

open class ThemeProvidingActivity : AppCompatActivity(), ThemeProvidingActivityContract.ThemeProvidingActivityView {

    private lateinit var presenter: ThemeProvidingActivityContract.ThemeProvidingActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = ThemeProvidingActivityPresenter(this)
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun createDBInstance() {
        DBHelper.getInstance(this)
    }

    override fun setTheme(theme: Theme?) {}

}