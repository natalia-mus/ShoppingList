package com.example.shoppinglist.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppinglist.ImageUtils
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

class MainActivity : ThemeProvidingActivity(), MainActivityContract.MainActivityView, OnItemClickAction {

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
        Settings.init(this)
    }

    override fun provideTheme(theme: Theme?) {
        val portraitBackground = theme?.listBackgroundImagePortrait ?: ResourcesCompat.getDrawable(resources, R.drawable.theme_grocery_list_portrait, null) ?.let { ImageUtils.getImageAsByteArray(it) }
        val landscapeBackground = theme?.listBackgroundImageLandscape ?: ResourcesCompat.getDrawable(resources, R.drawable.theme_grocery_list_landscape, null) ?.let { ImageUtils.getImageAsByteArray(it) }
        setTheme(portraitBackground, landscapeBackground, main_activity_container)
    }

    private fun openProductWithEditContext(product: Product) {
        val intent = Intent(this, AddProductActivity::class.java)
        intent.putExtra(Constants.PRODUCT, product)
        startActivity(intent)
    }

}