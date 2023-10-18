package com.example.shoppinglist.view

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppinglist.R
import com.example.shoppinglist.adapter.ThemesAdapter
import com.example.shoppinglist.constants.ThemeType
import com.example.shoppinglist.contract.ThemesActivityContract
import com.example.shoppinglist.model.Theme
import com.example.shoppinglist.presenter.ThemesActivityPresenter
import kotlinx.android.synthetic.main.activity_themes.*

class ThemesActivity : AppCompatActivity(), ThemesActivityContract.ThemesActivityView {

    private lateinit var presenter: ThemesActivityContract.ThemesActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_themes)

        presenter = ThemesActivityPresenter(this)
    }

    override fun initView(themes: ArrayList<Theme>?, actualThemeId: Int) {
        themes_list.layoutManager = LinearLayoutManager(this)
        themes?.let { themes_list.adapter = ThemesAdapter(this, it) }

        themes_button_save.setOnClickListener() {
            presenter.setTheme(actualThemeId)
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        themes_button_cancel.setOnClickListener() {
            onBackPressed()
        }
    }

    private fun checkOption(themeType: ThemeType, themeLayout: LinearLayout, themeName: TextView) {
        themeLayout.background = ResourcesCompat.getDrawable(resources, R.drawable.option_item_background_checked, null)
        themeName.setTextColor(resources.getColor(R.color.white, null))
    }

    private fun uncheckOption(themeLayout: LinearLayout, themeName: TextView) {
        themeLayout.background = ResourcesCompat.getDrawable(resources, R.drawable.option_item_background, null)
        themeName.setTextColor(resources.getColor(R.color.sea_blue_dark, null))
    }

}