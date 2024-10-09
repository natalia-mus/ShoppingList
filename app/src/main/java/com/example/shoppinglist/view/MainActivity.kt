package com.example.shoppinglist.view

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppinglist.R
import com.example.shoppinglist.Settings
import com.example.shoppinglist.adapter.OnItemClickAction
import com.example.shoppinglist.adapter.ProductAdapter
import com.example.shoppinglist.constants.Constants
import com.example.shoppinglist.contract.MainActivityContract
import com.example.shoppinglist.model.Product
import com.example.shoppinglist.model.Theme
import com.example.shoppinglist.presenter.MainActivityPresenter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Fragment(), MainActivityContract.MainActivityView, OnItemClickAction {

    private lateinit var presenter: MainActivityPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val fragmentView = inflater.inflate(R.layout.activity_main, container, false)
        presenter = MainActivityPresenter(this)
        presenter.showData()
        return fragmentView
    }

    override fun onResume() {
        super.onResume()
        presenter.showData()
    }

    override fun showData() {
        presenter.fetchData()
        val data = presenter.returnData()

//        if (context != null) {
//            shopping_list.layoutManager = LinearLayoutManager(context)
//            shopping_list.adapter = data?.let { ProductAdapter(context!!, it, this, getAppTheme()) }
//        }
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

        val dialog = context?.let { ConfirmationDialog(it, resources.getString(R.string.delete_product_question), confirmationDialogListener) }
        dialog?.show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_item_add_product -> {
                val intent = Intent(context, AddProductActivity::class.java)
                startActivity(intent)
            }
            R.id.menu_item_themes -> {
                val intent = Intent(context, ThemesActivity::class.java)
                startActivity(intent)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onItemLongClicked(product: Product) {
        openProductWithEditContext(product)
    }

//    override fun initSettings() {
//        Settings.init(this)
//    }

//    override fun provideTheme(theme: Theme?) {
//        setTheme(theme, main_activity_container, this)
//    }

    private fun openProductWithEditContext(product: Product) {
        val intent = Intent(context, AddProductActivity::class.java)
        intent.putExtra(Constants.PRODUCT, product)
        startActivity(intent)
    }

}