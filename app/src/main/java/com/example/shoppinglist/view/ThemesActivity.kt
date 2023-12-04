package com.example.shoppinglist.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppinglist.R
import com.example.shoppinglist.adapter.ThemesAdapter
import com.example.shoppinglist.contract.ThemesActivityContract
import com.example.shoppinglist.model.Theme
import com.example.shoppinglist.presenter.ThemesActivityPresenter
import kotlinx.android.synthetic.main.activity_themes.*
import kotlinx.android.synthetic.main.buttons_section.*

class ThemesActivity : AppCompatActivity(), ThemesActivityContract.ThemesActivityView, ThemeSelector {

    private lateinit var presenter: ThemesActivityContract.ThemesActivityPresenter
    private var selectedThemeType = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_themes)

        presenter = ThemesActivityPresenter(this)
    }

    override fun initView(themes: ArrayList<Theme>?, actualThemeId: Int) {
        if (themes != null) {
            selectedThemeType = actualThemeId
            themes_list.layoutManager = LinearLayoutManager(this)
            themes_list.adapter = ThemesAdapter(this, themes, selectedThemeType, this)

            themes_create_theme.setOnClickListener {
                val intent = Intent(this, CreateThemeActivity::class.java)
                startActivity(intent)
            }

            button_save.setOnClickListener {
                presenter.setTheme(selectedThemeType)
                val intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }

            button_cancel.setOnClickListener {
                onBackPressed()
            }
        }
    }

    override fun onThemeSelected(themeId: Int) {
        selectedThemeType = themeId
    }
}

interface ThemeSelector {
    fun onThemeSelected(themeId: Int)
}