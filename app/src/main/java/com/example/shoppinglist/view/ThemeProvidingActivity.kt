package com.example.shoppinglist.view

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.example.shoppinglist.ImageUtils
import com.example.shoppinglist.R
import com.example.shoppinglist.contract.ThemeProvidingActivityContract
import com.example.shoppinglist.database.DBHelper
import com.example.shoppinglist.model.Theme
import com.example.shoppinglist.presenter.ThemeProvidingActivityPresenter

abstract class ThemeProvidingActivity : AppCompatActivity(), ThemeProvidingActivityContract.ThemeProvidingActivityView {

    private lateinit var presenter: ThemeProvidingActivityContract.ThemeProvidingActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toolbar_providing)
        setToolbar()
        //setFragment(MainActivity())
        presenter = ThemeProvidingActivityPresenter(this)
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun createDBInstance() {
        DBHelper.getInstance(this)
    }

    override fun getAppTheme() = presenter.getTheme()

    abstract override fun provideTheme(theme: Theme?)

    protected fun setTheme(theme: Theme?, destination: View, activity: ThemeProvidingActivity) {
        val orientation = resources.configuration.orientation

        if (theme != null) {
            var backgroundImage: ByteArray? = null
            var backgroundColor: Int? = null


            if (activity is MainActivity) {
                if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                    if (theme.listBackgroundImagePortrait != null) {
                        backgroundImage = theme.listBackgroundImagePortrait

                    } else if (theme.listBackgroundColorPortrait != null) {
                        backgroundColor = theme.listBackgroundColorPortrait

                    } else {
                        backgroundImage = ImageUtils.getImageAsByteArray(ResourcesCompat.getDrawable(resources, R.drawable.theme_grocery_list_portrait, null))
                    }
                } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    if (theme.listBackgroundImageLandscape != null) {
                        backgroundImage = theme.listBackgroundImageLandscape

                    } else if (theme.listBackgroundColorLandscape != null) {
                        backgroundColor = theme.listBackgroundColorLandscape

                    } else {
                        backgroundImage = ImageUtils.getImageAsByteArray(ResourcesCompat.getDrawable(resources, R.drawable.theme_grocery_list_landscape, null))
                    }
                }

            }
//            else if (fragment is AddProductActivity) {
//                if (orientation == Configuration.ORIENTATION_PORTRAIT) {
//                    if (theme.addProductBackgroundImagePortrait != null) {
//                        backgroundImage = theme.addProductBackgroundImagePortrait
//
//                    } else if (theme.addProductBackgroundColorPortrait != null) {
//                        backgroundColor = theme.addProductBackgroundColorPortrait
//
//                    } else {
//                        backgroundImage = ImageUtils.getImageAsByteArray(ResourcesCompat.getDrawable(resources, R.drawable.theme_grocery_add_product_portrait, null))
//                    }
//                } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
//                    if (theme.addProductBackgroundImageLandscape != null) {
//                        backgroundImage = theme.addProductBackgroundImageLandscape
//
//                    } else if (theme.addProductBackgroundColorLandscape != null) {
//                        backgroundColor = theme.addProductBackgroundColorLandscape
//
//                    } else {
//                        backgroundImage = ImageUtils.getImageAsByteArray(ResourcesCompat.getDrawable(resources, R.drawable.theme_grocery_add_product_landscape, null))
//                    }
//                }
//
//                val addProductNameView = findViewById<EditText>(R.id.add_product_name)
//                val addProductQuantityView = findViewById<EditText>(R.id.add_product_quantity)
//                val addProductPriorityView = findViewById<EditText>(R.id.add_product_priority)
//
//                addProductNameView.setTextColor(theme.addProductTextColorValue)
//                addProductQuantityView.setTextColor(theme.addProductTextColorValue)
//                addProductPriorityView.setTextColor(theme.addProductTextColorValue)
//
//                addProductNameView.setHintTextColor(Color.parseColor(theme.addProductHintColorValue))
//                addProductQuantityView.setHintTextColor(Color.parseColor(theme.addProductHintColorValue))
//                addProductPriorityView.setHintTextColor(Color.parseColor(theme.addProductHintColorValue))
//
//                val colorStateList = ColorStateList.valueOf(theme.addProductLineColorValue)
//                addProductNameView.backgroundTintList = colorStateList
//                addProductQuantityView.backgroundTintList = colorStateList
//                addProductPriorityView.backgroundTintList = colorStateList
//
//                val addProductNameLabel = findViewById<TextView>(R.id.add_product_name_label)
//                val addProductQuantityLabel = findViewById<TextView>(R.id.add_product_quantity_label)
//                val addProductPriorityLabel = findViewById<TextView>(R.id.add_product_priority_label)
//
//                addProductNameLabel.setTextColor(theme.addProductLabelColorValue)
//                addProductQuantityLabel.setTextColor(theme.addProductLabelColorValue)
//                addProductPriorityLabel.setTextColor(theme.addProductLabelColorValue)
//            }


            if (backgroundImage != null) {
                val background = ImageUtils.getImageAsDrawable(this, backgroundImage)
                destination.background = background

            } else if (backgroundColor != null) {
                destination.setBackgroundColor(backgroundColor)
            }

        }
    }

    private fun setToolbar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
    }

    private fun setFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment, fragment)
            commit()
        }
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