package com.example.shoppinglist.model

data class Theme(
    val id: Int,
    val name: String,
    val listBackgroundPortrait: ByteArray?,
    val listBackgroundLandscape: ByteArray?,
    val addProductBackgroundPortrait: ByteArray?,
    val addProductBackgroundLandscape: ByteArray?,
)