package com.example.shoppinglist.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.shoppinglist.R
import com.example.shoppinglist.constants.SavingContext
import com.example.shoppinglist.contract.AddProductActivityContract
import com.example.shoppinglist.model.Product
import com.example.shoppinglist.presenter.AddProductActivityPresenter
import kotlinx.android.synthetic.main.activity_add_product.*

class AddProductActivity : AppCompatActivity(), AddProductActivityContract.AddProductActivityView {

    private var savingContext = SavingContext.CREATE
    private lateinit var presenter: AddProductActivityContract.AddProductActivityPresenter
    private lateinit var product: Product

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)

        checkContext()
        presenter = AddProductActivityPresenter(this)
    }

    private fun checkContext() {
        if (intent.hasExtra("product")) {
            savingContext = SavingContext.EDIT
            product = intent.getSerializableExtra("product") as Product
        }
    }

    private fun prepareProductData(product: Product) {
        add_product_name.setText(product.name)
        add_product_quantity.setText(product.quantity)
        add_product_priority.setText(product.priority.toString())
    }

    override fun initView() {
        if (savingContext == SavingContext.EDIT) prepareProductData(product)

        add_product_button_cancel.setOnClickListener() {
            onBackPressed()
        }

        add_product_button_save.setOnClickListener() {
            onSaveButtonClicked()
        }
    }

    private fun invalidateData(name: String, priority: String): Boolean {
        var result = true

        if (name.isNotEmpty() && priority.isNotEmpty()) {
            result = true
        } else if (name.isEmpty() || priority.isEmpty()) {
            var message = ""

            if (name.isEmpty()) {
                message = resources.getString(R.string.product_name_can_not_be_empty)
            } else if (priority.isEmpty()) {
                message = resources.getString(R.string.priority_can_not_be_empty)
            }

            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            result = false
        }
        return result
    }

    private fun onSaveButtonClicked() {
        val name = add_product_name.text.toString()
        val quantity = add_product_quantity.text.toString()
        val priority = add_product_priority.text.toString()

        if (invalidateData(name, priority)) {
            presenter.saveData(savingContext, product.id, name, quantity, priority.toInt())
            finish()
        }
    }

}