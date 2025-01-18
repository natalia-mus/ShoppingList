package com.example.shoppinglist.contract

import com.example.shoppinglist.model.ColorSet
import com.example.shoppinglist.model.Theme

interface ThemeProvidingActivityContract {

    interface ThemeProvidingActivityModel {
        fun getColorSet(): ColorSet?
        fun getTheme(): Theme?
    }

    interface ThemeProvidingActivityPresenter {
        fun getColorSet(): ColorSet?
        fun getTheme(): Theme?
        fun onResume()
    }

    interface ThemeProvidingActivityView {
        fun createDBInstance()
        fun getAppTheme(): Theme?
        fun provideTheme(theme: Theme?)
    }
}