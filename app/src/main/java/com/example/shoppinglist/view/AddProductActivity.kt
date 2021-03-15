package com.example.shoppinglist.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.shoppinglist.R
import com.example.shoppinglist.contract.AddProductActivityContract
import com.example.shoppinglist.presenter.AddProductActivityPresenter
import kotlinx.android.synthetic.main.activity_add_product.*

class AddProductActivity : AppCompatActivity(), AddProductActivityContract.AddProductActivityView {

    private lateinit var presenter: AddProductActivityContract.AddProductActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)

        presenter = AddProductActivityPresenter(this)
        presenter.test()
    }

    override fun initView() {
        add_product_button_cancel.setOnClickListener() {
            onBackPressed()
        }

        add_product_button_save.setOnClickListener() {
            // TODO
        }
    }

}