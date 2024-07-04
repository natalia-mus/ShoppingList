package com.example.shoppinglist.control

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Parcelable
import android.os.ResultReceiver
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View.OnClickListener
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.example.shoppinglist.ImageUtils
import com.example.shoppinglist.R
import com.example.shoppinglist.constants.Constants
import com.example.shoppinglist.view.ElementType
import com.google.android.material.bottomsheet.BottomSheetDialog
import yuku.ambilwarna.AmbilWarnaDialog

class ImageColorPicker @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val attributes = context.obtainStyledAttributes(attrs, R.styleable.ImageColorPicker)

    private val copyOptionLabel = attributes.getString(R.styleable.ImageColorPicker_copyOptionLabel)
    private val elementType = ElementType.getByElementTypeId(attributes.getInt(R.styleable.ImageColorPicker_elementType, ElementType.PRODUCT_ITEM_TEXT_COLOR.elementTypeId))
    private val labelColor = attributes.getColor(R.styleable.ImageColorPicker_labelColor, ResourcesCompat.getColor(resources, R.color.gray, null))
    private val label = attributes.getString(R.styleable.ImageColorPicker_label)

    private lateinit var border: LinearLayout
    private lateinit var labelView: TextView
    private lateinit var thumbnail: ImageView

    private var color = Color.WHITE
    private var isValueSet = false
    private var showCopyOption = false

    private var onClick = OnClickListener {
        if (canSetImageOrColor()) {
            selectBackgroundType()
        } else {
            openColorPicker()
        }
    }

    private var onBackgroundSetListener: OnBackgroundSetListener? = null


    init {
        orientation = VERTICAL
        gravity = Gravity.CENTER
        createView()
        setOnClickListener(onClick)
    }


    fun removeBackground() {
        val background = ResourcesCompat.getDrawable(resources, R.drawable.ic_create_theme, null)
        thumbnail.background = null
        thumbnail.setImageDrawable(background)
        setBorderVisibility(false)
        isValueSet = false
    }

    fun setOnBackgroundSetListener(onBackgroundSetListener: OnBackgroundSetListener) {
        this.onBackgroundSetListener = onBackgroundSetListener
    }

    fun setSelectedColor(color: Int) {
        thumbnail.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.round_background, null))
        thumbnail.drawable.setTint(color)
        setBorderVisibility(true)
        onBackgroundSetListener?.onColorSet(elementType, color)
        this.color = color
        isValueSet = true
    }

    fun setSelectedImage(background: ByteArray?) {
        if (background != null) {
            thumbnail.setImageDrawable(null)
            val drawable = ImageUtils.getImageAsDrawable(context, background)
            thumbnail.background = drawable
            isValueSet = true
        }
    }

    fun showCopyOption(show: Boolean) {
        showCopyOption = show
    }

    private fun canSetImageOrColor(): Boolean {
        return elementType != null
                && elementType == ElementType.PRODUCT_LIST_PORTRAIT_BACKGROUND
                || elementType == ElementType.PRODUCT_LIST_LANDSCAPE_BACKGROUND
                || elementType == ElementType.ADD_PRODUCT_PORTRAIT_BACKGROUND
                || elementType == ElementType.ADD_PRODUCT_LANDSCAPE_BACKGROUND
    }

    private fun copyBackground() {
        onBackgroundSetListener?.onCopyBackground(elementType)
    }

    private fun createView() {
        border = LinearLayout(context)
        border.layoutParams = LayoutParams(getDP(82), getDP(82))
        border.gravity = Gravity.CENTER
        border.setBackgroundResource(R.drawable.round_background)
        setBorderVisibility(!canSetImageOrColor())

        thumbnail = ImageView(context)
        thumbnail.layoutParams = LayoutParams(getDP(80), getDP(80))
        thumbnail.scaleType = ImageView.ScaleType.FIT_XY
        thumbnail.scaleType = ImageView.ScaleType.CENTER_CROP

        thumbnail.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ic_create_theme, null))
        thumbnail.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ic_create_theme, null))

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

    private fun openColorPicker() {
        AmbilWarnaDialog(context, color, object : AmbilWarnaDialog.OnAmbilWarnaListener {
            override fun onCancel(dialog: AmbilWarnaDialog?) {}

            override fun onOk(dialog: AmbilWarnaDialog?, color: Int) {
                setSelectedColor(color)
            }
        }).show()
    }

    private val resultReceiver = object : ResultReceiver(Handler()) {
        override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
            super.onReceiveResult(resultCode, resultData)
            val imageUri = resultData?.getString(GalleryActivity.IMAGE_URI)
            setBackgroundSource(resultCode, imageUri)
        }
    }

    private fun openGallery() {
        if (elementType != null) {
            val intent = Intent(context, GalleryActivity::class.java)
            intent.putExtra(GalleryActivity.RESULT_RECEIVER, resultReceiver)
            intent.putExtra(GalleryActivity.BACKGROUND_TYPE_ID, elementType.elementTypeId)
            (context as Activity).startActivity(intent)
        }
    }

    private fun selectBackgroundType() {
        val backgroundTypePanel = BottomSheetDialog(context)
        val backgroundTypePanelView = LayoutInflater.from(context).inflate(R.layout.panel_background_type, findViewById(R.id.panel_background_type_container))

        backgroundTypePanelView.findViewById<LinearLayout>(R.id.panel_background_type_image).setOnClickListener {
            openGallery()
            backgroundTypePanel.dismiss()
        }

        backgroundTypePanelView.findViewById<LinearLayout>(R.id.panel_background_type_color).setOnClickListener {
            openColorPicker()
            backgroundTypePanel.dismiss()
        }

        backgroundTypePanelView.findViewById<LinearLayout>(R.id.panel_background_type_remove).apply {
            visibility = if (isValueSet) VISIBLE else GONE
            if (isValueSet) {
                setOnClickListener {
                    removeBackground()
                    onBackgroundSetListener?.onRemoveBackground(elementType)
                    backgroundTypePanel.dismiss()
                }
            }
        }

        backgroundTypePanelView.findViewById<LinearLayout>(R.id.panel_background_type_copy).apply {
            visibility = if (showCopyOption) VISIBLE else GONE
            if (showCopyOption) {
                backgroundTypePanelView.findViewById<TextView>(R.id.panel_background_type_copy_label).text = copyOptionLabel
                setOnClickListener {
                    copyBackground()
                    backgroundTypePanel.dismiss()
                }
            }
        }

        backgroundTypePanel.setContentView(backgroundTypePanelView)
        backgroundTypePanel.show()
    }

    private fun setBackgroundSource(resultCode: Int, imageUri: String?) {
        if (resultCode == AppCompatActivity.RESULT_OK) {
            if (imageUri != null && imageUri != Constants.NULL) {
                val image = ImageUtils.getImageAsByteArray(context, Uri.parse(imageUri))
                setSelectedImage(image)

                if (elementType != null) {
                    onBackgroundSetListener?.onImageSet(elementType, image)
                }
            }
        }
    }

    private fun setBorderVisibility(visible: Boolean) {
        if (visible) {
            border.background.setTint(ResourcesCompat.getColor(resources, R.color.transparent_black, null))
        } else {
            border.background?.setTint(ResourcesCompat.getColor(resources, R.color.transparent, null))
        }
    }

    interface OnBackgroundSetListener {
        fun onCopyBackground(elementType: ElementType?)
        fun onColorSet(elementType: ElementType?, color: Int)
        fun onImageSet(elementType: ElementType?, image: ByteArray)
        fun onRemoveBackground(elementType: ElementType?)
    }
}

class GalleryActivity : Activity() {

    companion object {
        const val BACKGROUND_TYPE_ID = "background_type_id"
        const val IMAGE_URI = "image_uri"
        const val RESULT_RECEIVER = "result_receiver"
        private const val INTENT_TYPE_IMAGE = "image/*"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (intent.hasExtra(BACKGROUND_TYPE_ID)) {
            val backgroundTypeId = intent.getIntExtra(BACKGROUND_TYPE_ID, ElementType.PRODUCT_LIST_PORTRAIT_BACKGROUND.elementTypeId)

            if (backgroundTypeId != 0) {
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = INTENT_TYPE_IMAGE
                startActivityForResult(intent, backgroundTypeId)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val imageUri = data?.data.toString()
        (intent.getParcelableExtra<Parcelable>(RESULT_RECEIVER) as ResultReceiver).send(RESULT_OK, Bundle().apply { putString(IMAGE_URI, imageUri) })
        finish()
    }
}