package com.example.shoppinglist.view

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.example.shoppinglist.R
import com.example.shoppinglist.constants.Themes
import com.example.shoppinglist.contract.ThemesActivityContract
import com.example.shoppinglist.presenter.ThemesActivityPresenter
import kotlinx.android.synthetic.main.activity_themes.*

class ThemesActivity : AppCompatActivity(), ThemesActivityContract.ThemesActivityView {

    private lateinit var presenter: ThemesActivityContract.ThemesActivityPresenter
    private lateinit var selectedTheme: Themes

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_themes)

        presenter = ThemesActivityPresenter(this)
    }

    override fun initView(actualTheme: Themes) {
        selectedTheme = actualTheme
        when (actualTheme) {
            Themes.GROCERY -> {
                checkOption(Themes.GROCERY, theme_grocery, theme_name_grocery)
            }
            Themes.MARKETPLACE -> {
                checkOption(Themes.MARKETPLACE, theme_marketplace, theme_name_marketplace)
            }
            Themes.FASHION -> {
                checkOption(Themes.FASHION, theme_fashion, theme_name_fashion)
            }
            Themes.CHRISTMAS -> {
                checkOption(Themes.CHRISTMAS, theme_christmas, theme_name_christmas)
            }
        }

        theme_grocery.setOnClickListener() {
            checkOption(Themes.GROCERY, theme_grocery, theme_name_grocery)
        }

        theme_marketplace.setOnClickListener() {
            checkOption(Themes.MARKETPLACE, theme_marketplace, theme_name_marketplace)
        }

        theme_fashion.setOnClickListener() {
            checkOption(Themes.FASHION, theme_fashion, theme_name_fashion)
        }

        theme_christmas.setOnClickListener() {
            checkOption(Themes.CHRISTMAS, theme_christmas, theme_name_christmas)
        }

        themes_button_save.setOnClickListener() {
            presenter.setTheme(selectedTheme)
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        themes_button_cancel.setOnClickListener() {
            onBackPressed()
        }
    }

    private fun checkOption(theme: Themes, themeLayout: LinearLayout, themeName: TextView) {
        uncheckOption(theme_grocery, theme_name_grocery)
        uncheckOption(theme_marketplace, theme_name_marketplace)
        uncheckOption(theme_fashion, theme_name_fashion)
        uncheckOption(theme_christmas, theme_name_christmas)

        themeLayout.background = ResourcesCompat.getDrawable(resources, R.drawable.option_item_background_checked, null)
        themeName.setTextColor(resources.getColor(R.color.white, null))
        selectedTheme = theme
    }

    private fun uncheckOption(themeLayout: LinearLayout, themeName: TextView) {
        themeLayout.background = ResourcesCompat.getDrawable(resources, R.drawable.option_item_background, null)
        themeName.setTextColor(resources.getColor(R.color.sea_blue_dark, null))
    }

}