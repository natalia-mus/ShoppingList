package com.example.shoppinglist.view

import android.content.Intent
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import com.example.shoppinglist.R

abstract class ToolbarProvidingActivity : ThemeProvidingActivity() {

    fun setToolbar(layout: ViewGroup) {
        val toolbar = Toolbar(this)
        LayoutInflater.from(this).inflate(R.layout.toolbar, toolbar)

        val typedValue = TypedValue()
        this.theme.resolveAttribute(R.attr.actionBarSize, typedValue, true)
        val actionBarHeight = TypedValue.complexToDimensionPixelSize(typedValue.data, resources.displayMetrics)
        toolbar.layoutParams = android.widget.Toolbar.LayoutParams(android.widget.Toolbar.LayoutParams.MATCH_PARENT, actionBarHeight)

        layout.addView(toolbar)
        setSupportActionBar(toolbar)
        toolbar.background = ResourcesCompat.getDrawable(resources, R.drawable.toolbar_background, null)
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
}