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

    override fun getDefaultAddProductHintColorValue(): String? {
        return model.getDefaultAddProductHintColorValue()
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
        addProductHintColorValue: String,
        colorSetId: Int
    ) {
        model.saveTheme(
            name,
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
            addProductHintColorValue,
            colorSetId,
        )
    }

    override fun validateFirstStep(): ValidationResult {
        return model.validateFirstStep()
    }

    override fun validateSecondStep(): ValidationResult {
        return model.validateSecondStep()
    }

    override fun validateThirdStep(
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
        boldProductName: Boolean,
        addProductTextColorValue: Int,
        addProductLabelColorValue: Int,
        addProductHintColorValue: String,
        addProductLineColorValue: Int
    ): ValidationResult {
        return model.validateThirdStep(
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
            boldProductName,
            addProductTextColorValue,
            addProductLabelColorValue,
            addProductHintColorValue,
            addProductLineColorValue
        )
    }

    override fun validateLastStep(themeName: String): ValidationResult {
        return model.validateLastStep(themeName)
    }
}