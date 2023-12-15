package com.example.shoppinglist

enum class PredefinedTheme(val id: Int) {
    GROCERY(1),
    MARKETPLACE(2),
    FASHION(3),
    CHRISTMAS(4);


    companion object {
        fun isPredefinedTheme(themeId: Int): Boolean {
            for (theme in values()) {
                if (themeId == theme.id) return true
            }
            return false
        }
    }
}