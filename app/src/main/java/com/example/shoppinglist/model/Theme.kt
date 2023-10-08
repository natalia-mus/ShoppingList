package com.example.shoppinglist.model

data class Theme(
    val id: Int,
    val name: String,
    val listBackground: String,
    val addProductBackground: String
) {
    companion object {
        const val THEME_GROCERY_LIST = "theme_grocery_list"
        const val THEME_GROCERY_ADD_PRODUCT = "theme_grocery_add_product"
        const val THEME_MARKETPLACE_LIST = "theme_marketplace_list"
        const val THEME_MARKETPLACE_ADD_PRODUCT = "theme_marketplace_add_product"
        const val THEME_FASHION_LIST = "theme_fashion_list"
        const val THEME_FASHION_ADD_PRODUCT = "theme_fashion_add_product"
        const val THEME_CHRISTMAS_LIST = "theme_christmas_list"
        const val THEME_CHRISTMAS_ADD_PRODUCT = "theme_christmas_add_product"
    }
}