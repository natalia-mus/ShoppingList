package com.example.shoppinglist.control

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.example.shoppinglist.R
import com.example.shoppinglist.view.BackgroundType
import com.example.shoppinglist.view.ElementType
import com.google.android.material.bottomsheet.BottomSheetDialog

class BackgroundPicker @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val attributes = context.obtainStyledAttributes(attrs, R.styleable.BackgroundPicker)

    private val backgroundType = BackgroundType.getByBackgroundTypeId(attributes.getInt(R.styleable.BackgroundPicker_backgroundType, BackgroundType.PRODUCT_LIST_PORTRAIT_BACKGROUND.backgroundTypeId))
    private val labelColor = attributes.getColor(R.styleable.BackgroundPicker_labelColor, ResourcesCompat.getColor(resources, R.color.gray, null))
    private val label = attributes.getString(R.styleable.BackgroundPicker_label)

    private var onClick = OnClickListener {
        backgroundType?.let { it1 -> selectBackgroundType(it1) }
    }


    init {
        orientation = VERTICAL
        createView()
        setOnClickListener(onClick)
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

    private fun selectBackgroundType(backgroundType: BackgroundType) {
        val backgroundTypePanel = BottomSheetDialog(context)
        val backgroundTypePanelView = LayoutInflater.from(context).inflate(R.layout.panel_background_type, findViewById(R.id.panel_background_type_container))

        backgroundTypePanelView.findViewById<LinearLayout>(R.id.panel_background_type_image).setOnClickListener {
            //openGallery(backgroundType)
            backgroundTypePanel.dismiss()
        }

        backgroundTypePanelView.findViewById<LinearLayout>(R.id.panel_background_type_color).setOnClickListener {
            val elementType = ElementType.getByElementTypeId(backgroundType.backgroundTypeId)
            if (elementType != null) {
                //openColorPicker(elementType)
            }
            backgroundTypePanel.dismiss()
        }

        var copyOptionVisibility = View.GONE
        var copyOptionLabel = resources.getString(R.string.copy_portrait_background)
        var removeOptionVisibility = View.GONE
//        when (backgroundType) {
//            BackgroundType.PRODUCT_LIST_PORTRAIT_BACKGROUND -> {
//                if (productListPortraitBackgroundImage != null || productListPortraitBackgroundColor != null) {
//                    removeOptionVisibility = View.VISIBLE
//                }
//
//                if (productListLandscapeBackgroundImage != null || productListLandscapeBackgroundColor != null) {
//                    copyOptionVisibility = View.VISIBLE
//                    copyOptionLabel = resources.getString(R.string.copy_landscape_background)
//                }
//            }
//            BackgroundType.PRODUCT_LIST_LANDSCAPE_BACKGROUND -> {
//                if (productListLandscapeBackgroundImage != null || productListLandscapeBackgroundColor != null) {
//                    removeOptionVisibility = View.VISIBLE
//                }
//
//                if (productListPortraitBackgroundImage != null || productListPortraitBackgroundColor != null) {
//                    copyOptionVisibility = View.VISIBLE
//                }
//            }
//            BackgroundType.ADD_PRODUCT_PORTRAIT_BACKGROUND -> {
//                if (addProductPortraitBackgroundImage != null || addProductPortraitBackgroundColor != null) {
//                    removeOptionVisibility = View.VISIBLE
//                }
//
//                if (addProductLandscapeBackgroundImage != null || addProductLandscapeBackgroundColor != null) {
//                    copyOptionVisibility = View.VISIBLE
//                    copyOptionLabel = resources.getString(R.string.copy_landscape_background)
//                }
//            }
//            BackgroundType.ADD_PRODUCT_LANDSCAPE_BACKGROUND -> {
//                if (addProductLandscapeBackgroundImage != null || addProductLandscapeBackgroundColor != null) {
//                    removeOptionVisibility = View.VISIBLE
//                }
//
//                if (addProductPortraitBackgroundImage != null || addProductPortraitBackgroundColor != null) {
//                    copyOptionVisibility = View.VISIBLE
//                }
//            }
//        }


//        backgroundTypePanelView.findViewById<LinearLayout>(R.id.panel_background_type_remove).apply {
//            visibility = removeOptionVisibility
//            if (removeOptionVisibility == View.VISIBLE) {
//                setOnClickListener {
//                    removeBackground(backgroundType)
//                    backgroundTypePanel.dismiss()
//                }
//            }
//        }


//        backgroundTypePanelView.findViewById<LinearLayout>(R.id.panel_background_type_copy).apply {
//            visibility = copyOptionVisibility
//            if (copyOptionVisibility == View.VISIBLE) {
//                setOnClickListener {
//                    copyBackground(backgroundType)
//                    backgroundTypePanel.dismiss()
//                }
//            }
//        }

//        if (copyOptionVisibility == View.VISIBLE) {
//            backgroundTypePanelView.findViewById<TextView>(R.id.panel_background_type_copy_label).text = copyOptionLabel
//        }

        backgroundTypePanel.setContentView(backgroundTypePanelView)
        backgroundTypePanel.show()
    }
}