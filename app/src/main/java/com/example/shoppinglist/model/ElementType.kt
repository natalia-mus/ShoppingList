package com.example.shoppinglist.model

enum class ElementType(val elementTypeId: Int) {
    PRODUCT_LIST_PORTRAIT_BACKGROUND(1),
    PRODUCT_LIST_LANDSCAPE_BACKGROUND(2),
    ADD_PRODUCT_PORTRAIT_BACKGROUND(3),
    ADD_PRODUCT_LANDSCAPE_BACKGROUND(4),
    PRODUCT_ITEM_BACKGROUND_COLOR(5),
    PRODUCT_ITEM_TEXT_COLOR(6),
    DELETE_ICON_COLOR(7),
    ADD_PRODUCT_TEXT_COLOR(8),
    ADD_PRODUCT_LABEL_COLOR(9),
    ADD_PRODUCT_LINE_COLOR(10),
    ADD_PRODUCT_HINT_COLOR(11);


    companion object {
        fun getByElementTypeId(elementTypeId: Int): ElementType? {
            for (elementType in values()) {
                if (elementType.elementTypeId == elementTypeId) {
                    return elementType
                }
            }
            return null
        }
    }
}