package com.example.shoppinglist.view

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppinglist.R
import com.example.shoppinglist.Settings
import com.example.shoppinglist.adapter.OnItemClickAction
import com.example.shoppinglist.adapter.ProductAdapter
import com.example.shoppinglist.constants.Constants
import com.example.shoppinglist.contract.MainActivityContract
import com.example.shoppinglist.model.Product
import com.example.shoppinglist.presenter.MainActivityPresenter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : ToolbarProvidingActivity(true), MainActivityContract.MainActivityView, OnItemClickAction {

    private lateinit var presenter: MainActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setToolbar(main_activity, main_activity_content, resources.getString(R.string.activity_main))

        presenter = MainActivityPresenter(this)
        presenter.showData()
    }

    override fun onResume() {
        super.onResume()
        presenter.showData()
    }

    override fun showData() {
        presenter.fetchData()
        val data = presenter.returnData()

        shopping_list.layoutManager = LinearLayoutManager(this)
        shopping_list.adapter = data?.let { ProductAdapter(this, it, this, getAppTheme()) }
    }

    override fun deleteItem(id: Int) {
        presenter.deleteItem(id)
    }

    override fun onDeleteClicked(productId: Int) {
        val confirmationDialogListener = object : ConfirmationDialogListener {
            override fun onConfirmButtonClick() {
                deleteItem(productId)
                showData()
            }

            override fun onDeclineButtonClick() {}
        }

        val dialog = ConfirmationDialog(this, resources.getString(R.string.delete_product_question), confirmationDialogListener)
        dialog.show()
    }

    override fun onItemLongClicked(product: Product) {
        openProductWithEditContext(product)
    }

    override fun initSettings() {
        Settings.init(this)
    }

//    override fun provideTheme(theme: Theme?) {
//        setTheme(theme, main_activity, this)
//    }

    private fun openProductWithEditContext(product: Product) {
        val intent = Intent(this, AddProductActivity::class.java)
        intent.putExtra(Constants.PRODUCT, product)
        startActivity(intent)
    }

}