package com.example.shoppinglist.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
                theme_grocery.setBackgroundColor(resources.getColor(R.color.sea_blue_light, null))
                theme_name_grocery.setTextColor(resources.getColor(R.color.white, null))
            }
            Themes.MARKETPLACE -> {
                theme_marketplace.setBackgroundColor(
                    resources.getColor(
                        R.color.sea_blue_light,
                        null
                    )
                )
                theme_name_marketplace.setTextColor(resources.getColor(R.color.white, null))
            }
            Themes.FASHION -> {
                theme_fashion.setBackgroundColor(resources.getColor(R.color.sea_blue_light, null))
                theme_name_fashion.setTextColor(resources.getColor(R.color.white, null))
            }
            Themes.CHRISTMAS -> {
                theme_christmas.setBackgroundColor(resources.getColor(R.color.sea_blue_light, null))
                theme_name_christmas.setTextColor(resources.getColor(R.color.white, null))
            }
        }

        theme_grocery.setOnClickListener() {
            theme_grocery.setBackgroundColor(resources.getColor(R.color.sea_blue_light, null))
            theme_name_grocery.setTextColor(resources.getColor(R.color.white, null))

            theme_marketplace.setBackgroundColor(resources.getColor(R.color.white, null))
            theme_name_marketplace.setTextColor(resources.getColor(R.color.sea_blue_dark, null))

            theme_fashion.setBackgroundColor(resources.getColor(R.color.white, null))
            theme_name_fashion.setTextColor(resources.getColor(R.color.sea_blue_dark, null))

            theme_christmas.setBackgroundColor(resources.getColor(R.color.white, null))
            theme_name_christmas.setTextColor(resources.getColor(R.color.sea_blue_dark, null))

            selectedTheme = Themes.GROCERY
        }

        theme_marketplace.setOnClickListener() {
            theme_grocery.setBackgroundColor(resources.getColor(R.color.white, null))
            theme_name_grocery.setTextColor(resources.getColor(R.color.sea_blue_dark, null))

            theme_marketplace.setBackgroundColor(resources.getColor(R.color.sea_blue_light, null))
            theme_name_marketplace.setTextColor(resources.getColor(R.color.white, null))

            theme_fashion.setBackgroundColor(resources.getColor(R.color.white, null))
            theme_name_fashion.setTextColor(resources.getColor(R.color.sea_blue_dark, null))

            theme_christmas.setBackgroundColor(resources.getColor(R.color.white, null))
            theme_name_christmas.setTextColor(resources.getColor(R.color.sea_blue_dark, null))

            selectedTheme = Themes.MARKETPLACE
        }

        theme_fashion.setOnClickListener() {
            theme_grocery.setBackgroundColor(resources.getColor(R.color.white, null))
            theme_name_grocery.setTextColor(resources.getColor(R.color.sea_blue_dark, null))

            theme_marketplace.setBackgroundColor(resources.getColor(R.color.white, null))
            theme_name_marketplace.setTextColor(resources.getColor(R.color.sea_blue_dark, null))

            theme_fashion.setBackgroundColor(resources.getColor(R.color.sea_blue_light, null))
            theme_name_fashion.setTextColor(resources.getColor(R.color.white, null))

            theme_christmas.setBackgroundColor(resources.getColor(R.color.white, null))
            theme_name_christmas.setTextColor(resources.getColor(R.color.sea_blue_dark, null))

            selectedTheme = Themes.FASHION
        }

        theme_christmas.setOnClickListener() {
            theme_grocery.setBackgroundColor(resources.getColor(R.color.white, null))
            theme_name_grocery.setTextColor(resources.getColor(R.color.sea_blue_dark, null))

            theme_marketplace.setBackgroundColor(resources.getColor(R.color.white, null))
            theme_name_marketplace.setTextColor(resources.getColor(R.color.sea_blue_dark, null))

            theme_fashion.setBackgroundColor(resources.getColor(R.color.white, null))
            theme_name_fashion.setTextColor(resources.getColor(R.color.sea_blue_dark, null))

            theme_christmas.setBackgroundColor(resources.getColor(R.color.sea_blue_light, null))
            theme_name_christmas.setTextColor(resources.getColor(R.color.white, null))

            selectedTheme = Themes.CHRISTMAS
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

}