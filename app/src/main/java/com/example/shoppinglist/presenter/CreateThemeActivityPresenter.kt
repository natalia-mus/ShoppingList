package com.example.shoppinglist.presenter

import com.example.shoppinglist.ValidationResult
import com.example.shoppinglist.contract.CreateThemeActivityContract
import com.example.shoppinglist.model.CreateThemeActivityModel
import com.example.shoppinglist.model.Icon

class CreateThemeActivityPresenter(_view: CreateThemeActivityContract.CreateThemeActivityView) : CreateThemeActivityContract.CreateThemeActivityPresenter {

    private val view = _view
    private val model = CreateThemeActivityModel()

    init {
        view.initView()
    }

    override fun getDefaultDeleteIconColorValue(): Int? {
        return model.getDefaultDeleteIconColorValue()
    }

    override fun getDefaultProductItemBackgroundValue(): String? {
        return model.getDefaultProductItemBackgroundValue()
    }

    override fun getDefaultProductItemTextColorValue(): Int? {
        return model.getDefaultProductItemTextColorValue()
    }

    override fun getDefaultAddProductTextColorValue(): Int? {
        return model.getDefaultAddProductTextColorValue()
    }

    override fun getDefaultAddProductLabelColorValue(): Int? {
        return model.getDefaultAddProductLabelColorValue()
    }

    override fun getDefaultAddProductLineColorValue(): Int? {
        return model.getDefaultAddProductLineColorValue()
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
        addProductTextColor: Int,
        addProductLabelColor: Int,
        addProductLineColor: Int
    ) {
        model.saveTheme(
            name, listBackgroundImagePortrait, listBackgroundImageLandscape, addProductBackgroundImagePortrait, addProductBackgroundImageLandscape,
            listBackgroundColorPortrait, listBackgroundColorLandscape, addProductBackgroundColorPortrait, addProductBackgroundColorLandscape, productItemBackgroundValue,
            productItemTextColorValue, deleteIconColorValue, deleteIcon, boldProductName, addProductTextColor, addProductLabelColor, addProductLineColor
        )
    }

    override fun validateFirstStep(): ValidationResult {
        return model.validateFirstStep()
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
        return model.validateSecondStep(
            productListPortraitBackgroundImage,
            productListLandscapeBackgroundImage,
            addProductPortraitBackgroundImage,
            addProductLandscapeBackgroundImage,
            productListPortraitBackgroundColor,
            productListLandscapeBackgroundColor,
            addProductPortraitBackgroundColor,
            addProductLandscapeBackgroundColor,
            productItemBackgroundValue,
            productItemTextColorValue,
            deleteIconColorValue,
            deleteIcon,
            boldProductName
        )
    }

    override fun validateLastStep(themeName: String): ValidationResult {
        return model.validateLastStep(themeName)
    }
}