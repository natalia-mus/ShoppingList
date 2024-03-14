package com.example.shoppinglist.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppinglist.R
import com.example.shoppinglist.adapter.ThemesAdapter
import com.example.shoppinglist.constants.Constants
import com.example.shoppinglist.contract.ThemesActivityContract
import com.example.shoppinglist.model.Theme
import com.example.shoppinglist.presenter.ThemesActivityPresenter
import kotlinx.android.synthetic.main.activity_themes.*
import kotlinx.android.synthetic.main.buttons_section.*

class ThemesActivity : AppCompatActivity(), ThemesActivityContract.ThemesActivityView, ThemeSelector {

    private lateinit var presenter: ThemesActivityContract.ThemesActivityPresenter
    private lateinit var themesAdapter: ThemesAdapter
    private var selectedThemeId = Constants.DEFAULT_THEME_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_themes)

        presenter = ThemesActivityPresenter(this)
    }

    override fun onResume() {
        super.onResume()
        presenter.onViewResume()
    }

    override fun initView(themes: ArrayList<Theme>?, actualThemeId: Int) {
        if (themes != null) {
            selectedThemeId = actualThemeId
            themes_list.layoutManager = LinearLayoutManager(this)
            themesAdapter = ThemesAdapter(this, themes, selectedThemeId, this)
            themes_list.adapter = themesAdapter

            themes_create_theme.setOnClickListener {
                val intent = Intent(this, CreateThemeActivity::class.java)
                startActivity(intent)
            }

            button_save.setOnClickListener {
                presenter.setTheme(selectedThemeId)
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

    override fun onDeleteThemeClicked(themeId: Int) {
        val deleteThemeDialogListener = object : ConfirmationDialogListener {
            override fun onConfirmButtonClick() {
                deleteTheme(themeId)
            }

            override fun onDeclineButtonClick() { }
        }

        val dialog = ConfirmationDialog(this, resources.getString(R.string.delete_theme_question), deleteThemeDialogListener)
        dialog.show()
    }

    override fun onThemeSelected(themeId: Int) {
        selectedThemeId = themeId
    }

    override fun refreshSelection(actualThemeId: Int) {
        themesAdapter.setSelectedThemeId(actualThemeId)
    }

    override fun refreshThemesList(themes: ArrayList<Theme>?) {
        if (themes != null) {
            themesAdapter.dataSetChanged(themes)
        }
    }

    private fun deleteTheme(themeId: Int) {
        presenter.deleteTheme(themeId)
    }
}

interface ThemeSelector {
    fun onDeleteThemeClicked(themeId: Int)
    fun onThemeSelected(themeId: Int)
}