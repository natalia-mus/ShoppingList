package com.example.shoppinglist.model

data class Theme(
    val id: Int,
    val name: String,
    val listBackgroundImagePortrait: ByteArray?,
    val listBackgroundImageLandscape: ByteArray?,
    val addProductBackgroundImagePortrait: ByteArray?,
    val addProductBackgroundImageLandscape: ByteArray?,
    val listBackgroundColorPortrait: Int?,
    val listBackgroundColorLandscape: Int?,
    val addProductBackgroundColorPortrait: Int?,
    val addProductBackgroundColorLandscape: Int?
)