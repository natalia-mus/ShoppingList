package com.example.shoppinglist.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppinglist.R
import com.example.shoppinglist.adapter.OnItemClickAction
import com.example.shoppinglist.adapter.ProductAdapter
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

    override fun DBinstance(): DBHelper {
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
        val intent = Intent(this, AddProductActivity::class.java)
        startActivity(intent)
        return super.onOptionsItemSelected(item)
    }

    override fun onItemLongClicked(product: Product) {
        openProductWithEditContext(product)
    }

    private fun openProductWithEditContext(product: Product) {
        val intent = Intent(this, AddProductActivity::class.java)
        intent.putExtra("product", product)
        startActivity(intent)
    }

}