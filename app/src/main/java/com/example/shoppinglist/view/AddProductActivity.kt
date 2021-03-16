package com.example.shoppinglist.view

import android.os.Bundle
import android.widget.Toast
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
    }

    override fun initView() {
        add_product_button_cancel.setOnClickListener() {
            onBackPressed()
        }

        add_product_button_save.setOnClickListener() {
            val name = add_product_name.text.toString()
            val amount = add_product_amount.text.toString()
            val priority = add_product_priority.text.toString()

            if (checkData(name, priority)) {
                presenter.saveData(name, amount, priority.toInt())
                finish()
            }
        }
    }

    private fun checkData(name: String, priority: String): Boolean {
        var result = true

        if (name.isNotEmpty() && priority.isNotEmpty()) {
            result = true
        } else if (name.isEmpty()) {
            Toast.makeText(this, "Product name can not be empty.", Toast.LENGTH_SHORT).show()
            result = false
        } else if (priority.isEmpty()) {
            Toast.makeText(this, "Priority can not be empty.", Toast.LENGTH_SHORT).show()
            result = false
        }
        return result
    }

}