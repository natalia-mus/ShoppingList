package com.example.shoppinglist.view

import android.os.Bundle
import android.widget.Toast
import com.example.shoppinglist.R
import com.example.shoppinglist.SavingContext
import com.example.shoppinglist.ValidationResult
import com.example.shoppinglist.constants.Constants
import com.example.shoppinglist.contract.AddProductActivityContract
import com.example.shoppinglist.model.Product
import com.example.shoppinglist.presenter.AddProductActivityPresenter
import kotlinx.android.synthetic.main.activity_add_product.*
import kotlinx.android.synthetic.main.buttons_section.*

class AddProductActivity : ToolbarProvidingActivity(false), AddProductActivityContract.AddProductActivityView {

    private var savingContext = SavingContext.CREATE
    private lateinit var presenter: AddProductActivityContract.AddProductActivityPresenter
    private lateinit var product: Product

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)
        setToolbar(add_product_activity)

        presenter = AddProductActivityPresenter(this)
    }

    override fun initView() {
        checkContext()
        if (savingContext == SavingContext.EDIT) prepareProductData(product)

        button_cancel.setOnClickListener {
            onBackPressed()
        }

        button_save.setOnClickListener {
            onSaveButtonClicked()
        }
    }

//    override fun provideTheme(theme: Theme?) {
//        setTheme(theme, add_product_activity, this)
//    }

    private fun checkContext() {
        if (intent.hasExtra(Constants.PRODUCT)) {
            savingContext = SavingContext.EDIT
            product = intent.getSerializableExtra(Constants.PRODUCT) as Product
        }
    }

    private fun prepareProductData(product: Product) {
        add_product_name.setText(product.name)
        add_product_quantity.setText(product.quantity)
        add_product_priority.setText(product.priority.toString())
    }

    private fun onSaveButtonClicked() {
        val name = add_product_name.text.toString()
        val quantity = add_product_quantity.text.toString()
        val priority = add_product_priority.text.toString()

        var id: Int? = null
        if (savingContext == SavingContext.EDIT) id = product.id

        val result = presenter.saveData(savingContext, id, name, quantity, priority)

        when (result) {
            ValidationResult.EMPTY_NAME -> {
                Toast.makeText(this, getString(R.string.product_name_can_not_be_empty), Toast.LENGTH_LONG).show()

            }
            ValidationResult.EMPTY_PRIORITY -> {
                Toast.makeText(this, getString(R.string.priority_can_not_be_empty), Toast.LENGTH_LONG).show()

            }
            else -> finish()
        }
    }

}