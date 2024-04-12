package com.example.shoppinglist.model

data class Theme(
    val id: Int,
    val name: String,
    val builtInTheme: Boolean,
    val listBackgroundImagePortrait: ByteArray?,
    val listBackgroundImageLandscape: ByteArray?,
    val addProductBackgroundImagePortrait: ByteArray?,
    val addProductBackgroundImageLandscape: ByteArray?,
    val listBackgroundColorPortrait: Int?,
    val listBackgroundColorLandscape: Int?,
    val addProductBackgroundColorPortrait: Int?,
    val addProductBackgroundColorLandscape: Int?
)

enum class Icon(val iconId: Int) {
    TRASH_BIN(101),
    CROSS(102)
}