package com.example.shoppinglist

class Color(private var colorInt: Int?, private var alphaPercentage: Float = DEFAULT_ALPHA_PERCENTAGE) {

    companion object {
        private const val DEFAULT_ALPHA_PERCENTAGE = 1f
    }

    private var alpha: String? = null
    private var color: String? = null
    private var value: String? = null

    init {
        setAlpha(alphaPercentage)
        if (colorInt != null) {
            setColor(colorInt!!)
        }
    }

    fun getAlpha() = alpha

    fun setAlpha(alphaPercentage: Float) {
        val alphaInt = (255 * alphaPercentage).toInt()
        var alphaValue = Integer.toHexString(alphaInt)

        if (alphaValue.length == 1) {
            alphaValue = "0$alphaValue"
        }

        alpha = alphaValue
    }

    fun getAlphaPercentage() = alphaPercentage

    fun getColor() = color

    fun getColorInt() = colorInt

    fun setColor(color: Int) {
        colorInt = color
        val colorValue = Integer.toHexString(color)
        val alpha = if (colorValue.length == 8) 2 else 0
        this.color = colorValue.drop(alpha)
    }

    fun getValue(): String {
        return "#$alpha$color"
    }

    fun removeColor() {
        alpha = null
        alphaPercentage = DEFAULT_ALPHA_PERCENTAGE
        color = null
        colorInt = null
        value = null
    }
}