package com.example.shoppinglist.control

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.example.shoppinglist.R

class BackgroundPicker @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val attributes = context.obtainStyledAttributes(attrs, R.styleable.BackgroundPicker)

    private val labelColor = attributes.getColor(R.styleable.BackgroundPicker_labelColor, ResourcesCompat.getColor(resources, R.color.gray, null))
    private val label = attributes.getString(R.styleable.BackgroundPicker_label)


    init {
        orientation = VERTICAL
        createView()
    }


    private fun createView() {
        val icon = ImageView(context)
        icon.layoutParams = LayoutParams(getDP(80), getDP(80))
        icon.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ic_create_theme, null))
        icon.scaleType = ImageView.ScaleType.CENTER_CROP


        val labelView = TextView(context)
        labelView.gravity = Gravity.CENTER
        labelView.setPadding(0, getDP(8), 0, 0)
        labelView.setTextColor(labelColor)
        labelView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getDP(14).toFloat())
        labelView.typeface = Typeface.DEFAULT_BOLD
        labelView.text = label


        addView(icon)
        addView(labelView)
    }

    private fun getDP(dp: Int) = (dp * resources.displayMetrics.density).toInt()
}