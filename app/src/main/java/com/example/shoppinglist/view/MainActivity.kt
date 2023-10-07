package com.example.shoppinglist.view

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppinglist.R
import com.example.shoppinglist.Settings
import com.example.shoppinglist.adapter.OnItemClickAction
import com.example.shoppinglist.adapter.ProductAdapter
import com.example.shoppinglist.constants.Constants
import com.example.shoppinglist.constants.ThemeType
import com.example.shoppinglist.contract.MainActivityContract
import com.example.shoppinglist.database.DBHelper
import com.example.shoppinglist.model.Product
import com.example.shoppinglist.presenter.MainActivityPresenter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainActivityContract.MainActivityView, OnItemClickAction {

    private lateinit var presenter: MainActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = MainActivityPresenter(this)
        presenter.showData()
    }

    override fun onResume() {
        super.onResume()
        presenter.showData()
    }

    override fun createDBinstance(): DBHelper {
        return DBHelper.getInstance(this)
    }

    override fun showData() {
        presenter.fetchData()
        val data = presenter.returnData()

        shopping_list.layoutManager = LinearLayoutManager(this)
        shopping_list.adapter = data?.let { ProductAdapter(this, this, it, this) }
    }

    override fun deleteItem(id: Int) {
        presenter.deleteItem(id)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_item_add_product -> {
                val intent = Intent(this, AddProductActivity::class.java)
                startActivity(intent)
            }
            R.id.menu_item_themes -> {
                val intent = Intent(this, ThemesActivity::class.java)
                startActivity(intent)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onItemLongClicked(product: Product) {
        openProductWithEditContext(product)
    }

    override fun initSettings() {
        Settings.getInstance(this)
    }

    override fun setTheme(themeType: ThemeType) {
        val orientation = resources.configuration.orientation
        when (themeType) {
            ThemeType.GROCERY -> {
                if (orientation == Configuration.ORIENTATION_PORTRAIT)  main_activity_container.setBackgroundResource(R.drawable.grocery_1_portrait)
                else main_activity_container.setBackgroundResource(R.drawable.grocery_1_landscape)
            }
            ThemeType.MARKETPLACE -> {
                if (orientation == Configuration.ORIENTATION_PORTRAIT)  main_activity_container.setBackgroundResource(R.drawable.marketplace_1_portrait)
                else main_activity_container.setBackgroundResource(R.drawable.marketplace_1_landscape)
            }
            ThemeType.FASHION -> {
                if (orientation == Configuration.ORIENTATION_PORTRAIT)  main_activity_container.setBackgroundResource(R.drawable.fashion_1_portrait)
                else main_activity_container.setBackgroundResource(R.drawable.fashion_1_landscape)
            }
            ThemeType.CHRISTMAS -> {
                if (orientation == Configuration.ORIENTATION_PORTRAIT)  main_activity_container.setBackgroundResource(R.drawable.christmas_1_portrait)
                else main_activity_container.setBackgroundResource(R.drawable.christmas_1_landscape)
            }
        }
    }

    private fun openProductWithEditContext(product: Product) {
        val intent = Intent(this, AddProductActivity::class.java)
        intent.putExtra(Constants.PRODUCT, product)
        startActivity(intent)
    }

}