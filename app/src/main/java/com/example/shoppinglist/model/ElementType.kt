package com.example.shoppinglist.model

enum class ElementType(val elementTypeId: Int) {
    PRODUCT_LIST_PORTRAIT_BACKGROUND(101),
    PRODUCT_LIST_LANDSCAPE_BACKGROUND(102),
    ADD_PRODUCT_PORTRAIT_BACKGROUND(103),
    ADD_PRODUCT_LANDSCAPE_BACKGROUND(104),
    PRODUCT_ITEM_BACKGROUND_COLOR(105),
    PRODUCT_ITEM_TEXT_COLOR(106),
    DELETE_ICON_COLOR(107),
    ADD_PRODUCT_TEXT_COLOR(108),
    ADD_PRODUCT_LABEL_COLOR(109);


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