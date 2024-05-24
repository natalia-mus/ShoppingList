package com.example.shoppinglist.control

import android.content.Context
import android.graphics.Color
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
import yuku.ambilwarna.AmbilWarnaDialog

class BackgroundPicker @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val attributes = context.obtainStyledAttributes(attrs, R.styleable.BackgroundPicker)

    private val backgroundType = BackgroundType.getByBackgroundTypeId(attributes.getInt(R.styleable.BackgroundPicker_backgroundType, BackgroundType.PRODUCT_LIST_PORTRAIT_BACKGROUND.backgroundTypeId))
    private val labelColor = attributes.getColor(R.styleable.BackgroundPicker_labelColor, ResourcesCompat.getColor(resources, R.color.gray, null))
    private val label = attributes.getString(R.styleable.BackgroundPicker_label)

    private lateinit var border: LinearLayout
    private lateinit var labelView: TextView
    private lateinit var thumbnail: ImageView

    private var onClick = OnClickListener {
        backgroundType?.let { it1 -> selectBackgroundType(it1) }
    }


    init {
        orientation = VERTICAL
        createView()
        setOnClickListener(onClick)
    }


    private fun createView() {
        border = LinearLayout(context)
        border.layoutParams = LayoutParams(getDP(82), getDP(82))
        border.gravity = Gravity.CENTER
        border.setBackgroundResource(R.drawable.round_background)

        thumbnail = ImageView(context)
        thumbnail.layoutParams = LayoutParams(getDP(80), getDP(80))
        thumbnail.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ic_create_theme, null))
        thumbnail.scaleType = ImageView.ScaleType.CENTER_CROP

        border.addView(thumbnail)


        labelView = TextView(context)
        labelView.gravity = Gravity.CENTER
        labelView.setPadding(0, getDP(8), 0, 0)
        labelView.setTextColor(labelColor)
        labelView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getDP(14).toFloat())
        labelView.typeface = Typeface.DEFAULT_BOLD
        labelView.text = label


        addView(border)
        addView(labelView)
    }

    private fun getDP(dp: Int) = (dp * resources.displayMetrics.density).toInt()

    private fun openColorPicker(elementType: ElementType) {
        AmbilWarnaDialog(context, Color.MAGENTA, object : AmbilWarnaDialog.OnAmbilWarnaListener {
            override fun onCancel(dialog: AmbilWarnaDialog?) {}

            override fun onOk(dialog: AmbilWarnaDialog?, color: Int) {
                setColor(elementType, color)
            }
        }).show()
    }

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
                openColorPicker(elementType)
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

    private fun setColor(elementType: ElementType, color: Int?) {
        when (elementType) {
            ElementType.PRODUCT_LIST_PORTRAIT_BACKGROUND -> {
                //productListPortraitBackgroundColor = color
            }
            ElementType.PRODUCT_LIST_LANDSCAPE_BACKGROUND -> {
                //productListLandscapeBackgroundColor = color
            }
            ElementType.ADD_PRODUCT_PORTRAIT_BACKGROUND -> {
                //addProductPortraitBackgroundColor = color
            }
            ElementType.ADD_PRODUCT_LANDSCAPE_BACKGROUND -> {
                //addProductLandscapeBackgroundColor = color
            }
            ElementType.PRODUCT_ITEM_BACKGROUND_COLOR -> {
                if (color != null) {
                    //setProductItemBackgroundColor(color)
                }
            }
            ElementType.PRODUCT_ITEM_TEXT_COLOR -> {
                if (color != null) {
                    //setProductItemTextColor(color)
                }
            }
            ElementType.DELETE_ICON_COLOR -> {
                if (color != null) {
                    //setDeleteIconColor(color)
                }
            }
        }

        if (color != null) setSelectedColor(color)
    }

    private fun setSelectedColor(color: Int) {
        thumbnail.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.round_background, null))
        thumbnail.drawable.setTint(color)
        border.background.setTint(ResourcesCompat.getColor(resources, R.color.transparent_black, null))
    }
}