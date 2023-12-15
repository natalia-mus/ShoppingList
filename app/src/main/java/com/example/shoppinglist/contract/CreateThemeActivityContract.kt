package com.example.shoppinglist.contract

import android.content.Context
import android.net.Uri
import com.example.shoppinglist.ValidationResult

interface CreateThemeActivityContract {

    interface CreateThemeActivityModel {
        fun saveTheme(
            name: String,
            listBackgroundPortrait: ByteArray?,
            listBackgroundLandscape: ByteArray?,
            addProductBackgroundPortrait: ByteArray?,
            addProductBackgroundLandscape: ByteArray?
        ): ValidationResult
    }

    interface CreateThemeActivityPresenter {
        fun getImageAsByteArray(context: Context, uri: Uri): ByteArray

        fun saveTheme(
            name: String,
            listBackgroundPortrait: ByteArray?,
            listBackgroundLandscape: ByteArray?,
            addProductBackgroundPortrait: ByteArray?,
            addProductBackgroundLandscape: ByteArray?
        ): ValidationResult
    }

    interface CreateThemeActivityView {
        fun initView()
    }
}