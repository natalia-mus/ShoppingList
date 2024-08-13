package com.example.shoppinglist.model

import com.example.shoppinglist.ValidationResult
import com.example.shoppinglist.constants.Constants
import com.example.shoppinglist.contract.CreateThemeActivityContract
import com.example.shoppinglist.database.DBHelper

class CreateThemeActivityModel : CreateThemeActivityContract.CreateThemeActivityModel {

    private val database = DBHelper.getInstance()

    private var defaultTheme: Theme? = null

    override fun getDefaultDeleteIconColorValue(): Int? {
        return getDefaultTheme()?.deleteIconColorValue
    }

    override fun getDefaultProductItemBackgroundValue(): String? {
        return getDefaultTheme()?.productItemBackgroundValue
    }

    override fun getDefaultProductItemTextColorValue(): Int? {
        return getDefaultTheme()?.productItemTextColorValue
    }

    override fun getDefaultAddProductTextColorValue(): Int? {
        return getDefaultTheme()?.addProductTextColorValue
    }

    override fun getDefaultAddProductLabelColorValue(): Int? {
        return getDefaultTheme()?.addProductLabelColorValue
    }

    override fun getDefaultAddProductLineColorValue(): Int? {
        return getDefaultTheme()?.addProductLineColorValue
    }

    override fun saveTheme(
        name: String,
        listBackgroundImagePortrait: ByteArray?,
        listBackgroundImageLandscape: ByteArray?,
        addProductBackgroundImagePortrait: ByteArray?,
        addProductBackgroundImageLandscape: ByteArray?,
        listBackgroundColorPortrait: Int?,
        listBackgroundColorLandscape: Int?,
        addProductBackgroundColorPortrait: Int?,
        addProductBackgroundColorLandscape: Int?,
        productItemBackgroundValue: String,
        productItemTextColorValue: Int,
        deleteIconColorValue: Int,
        deleteIcon: Icon,
        boldProductName: Boolean,
        addProductTextColorValue: Int,
        addProductLabelColorValue: Int,
        addProductLineColorValue: Int,
        addProductHintColorValue: String
    ) {
        database?.saveTheme(
            name,
            false,
            listBackgroundImagePortrait,
            listBackgroundImageLandscape,
            addProductBackgroundImagePortrait,
            addProductBackgroundImageLandscape,
            listBackgroundColorPortrait,
            listBackgroundColorLandscape,
            addProductBackgroundColorPortrait,
            addProductBackgroundColorLandscape,
            productItemBackgroundValue,
            productItemTextColorValue,
            deleteIconColorValue,
            deleteIcon,
            boldProductName,
            addProductTextColorValue,
            addProductLabelColorValue,
            addProductLineColorValue,
            addProductHintColorValue
        )
    }

    override fun validateFirstStep(): ValidationResult {
        return ValidationResult.VALID       // first step is always valid
    }

    override fun validateSecondStep(
        productListPortraitBackgroundImage: ByteArray?,
        productListLandscapeBackgroundImage: ByteArray?,
        addProductPortraitBackgroundImage: ByteArray?,
        addProductLandscapeBackgroundImage: ByteArray?,
        productListPortraitBackgroundColor: Int?,
        productListLandscapeBackgroundColor: Int?,
        addProductPortraitBackgroundColor: Int?,
        addProductLandscapeBackgroundColor: Int?,
        productItemBackgroundValue: String,
        productItemTextColorValue: Int,
        deleteIconColorValue: Int,
        deleteIcon: Icon,
        boldProductName: Boolean
    ): ValidationResult {
        val defaultTheme = getDefaultTheme()

        val differsFromDefaultTheme = productListPortraitBackgroundImage != null
                || productListLandscapeBackgroundImage != null
                || addProductPortraitBackgroundImage != null
                || addProductLandscapeBackgroundImage != null
                || productListPortraitBackgroundColor != null
                || productListLandscapeBackgroundColor != null
                || addProductPortraitBackgroundColor != null
                || addProductLandscapeBackgroundColor != null
                || defaultTheme != null
                && (productItemBackgroundValue != defaultTheme.productItemBackgroundValue
                || productItemTextColorValue != defaultTheme.productItemTextColorValue
                || deleteIconColorValue != defaultTheme.deleteIconColorValue
                || deleteIcon != defaultTheme.deleteIcon
                || boldProductName != defaultTheme.boldProductName)

        return if (differsFromDefaultTheme) ValidationResult.VALID else ValidationResult.NOTHING_TO_KEEP
    }

    override fun validateLastStep(themeName: String): ValidationResult {
        return if (themeName.isEmpty()) {
            ValidationResult.EMPTY_NAME
        } else return ValidationResult.VALID
    }

    private fun getDefaultTheme(): Theme? {
        if (defaultTheme == null) {
            defaultTheme = database?.getTheme(Constants.DEFAULT_THEME_ID)
        }
        return defaultTheme
    }
}