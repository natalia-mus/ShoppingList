package com.example.shoppinglist.model

import com.example.shoppinglist.ImageSourceType
import com.example.shoppinglist.PredefinedTheme

data class Theme(
    val id: Int,
    val name: String,
    val listBackgroundPortrait: ByteArray,
    val listBackgroundLandscape: ByteArray,
    val addProductBackgroundPortrait: ByteArray,
    val addProductBackgroundLandscape: ByteArray,
) {
    fun getBackgroundImageSourceType(): ImageSourceType {
        return if (PredefinedTheme.isPredefinedTheme(id)) {
            ImageSourceType.RESOURCE_ID
        } else ImageSourceType.URI
    }
}