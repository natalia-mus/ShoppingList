package com.example.shoppinglist.contract

import com.example.shoppinglist.model.Theme

interface ThemeProvidingActivityContract {

    interface ThemeProvidingActivityModel {
        fun getTheme(): Theme?
    }

    interface ThemeProvidingActivityPresenter {
        fun getTheme(): Theme?
        fun onResume()
    }

    interface ThemeProvidingActivityView {
        fun createDBInstance()
        fun getAppTheme(): Theme?
        fun provideTheme(theme: Theme?)
    }
}