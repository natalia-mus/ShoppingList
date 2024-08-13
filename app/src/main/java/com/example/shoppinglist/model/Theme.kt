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
    val addProductBackgroundColorLandscape: Int?,
    val productItemBackgroundValue: String,
    val productItemTextColorValue: Int,
    val deleteIconColorValue: Int,
    val deleteIcon: Icon,
    val boldProductName: Boolean,
    val addProductTextColorValue: Int,
    val addProductLabelColorValue: Int,
    val addProductLineColorValue: Int,
    val addProductHintColorValue: String
)

enum class Icon(val iconId: Int) {
    TRASH_BIN(101),
    CROSS(102);

    companion object {
        fun getByIconId(iconId: Int): Icon? {
            for (icon in values()) {
                if (icon.iconId == iconId) {
                    return icon
                }
            }
            return null
        }
    }
}