package com.example.shoppinglist.view

import android.content.res.Configuration
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.shoppinglist.ImageUtils
import com.example.shoppinglist.contract.ThemeProvidingActivityContract
import com.example.shoppinglist.database.DBHelper
import com.example.shoppinglist.model.Theme
import com.example.shoppinglist.presenter.ThemeProvidingActivityPresenter

abstract class ThemeProvidingActivity : AppCompatActivity(), ThemeProvidingActivityContract.ThemeProvidingActivityView {

    private lateinit var presenter: ThemeProvidingActivityContract.ThemeProvidingActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = ThemeProvidingActivityPresenter(this)
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun createDBInstance() {
        DBHelper.getInstance(this)
    }

    abstract override fun provideTheme(theme: Theme?)

    protected fun setTheme(portraitBackground: ByteArray?, landscapeBackground: ByteArray?, destination: View) {
        val orientation = resources.configuration.orientation

        var background: Drawable? = null
        if (orientation == Configuration.ORIENTATION_PORTRAIT && portraitBackground != null) {
            background = ImageUtils.getImageAsDrawable(this, portraitBackground)

        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE && landscapeBackground != null) {
            background = ImageUtils.getImageAsDrawable(this, landscapeBackground)
        }

        if (background != null) {
            destination.background = background
        }
    }

}