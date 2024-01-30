package com.example.shoppinglist.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.example.shoppinglist.ImageUtils
import com.example.shoppinglist.R
import com.example.shoppinglist.ValidationResult
import com.example.shoppinglist.contract.CreateThemeActivityContract
import com.example.shoppinglist.presenter.CreateThemeActivityPresenter
import com.google.android.material.bottomsheet.BottomSheetDialog
import yuku.ambilwarna.AmbilWarnaDialog

class CreateThemeActivity : AppCompatActivity(), CreateThemeActivityContract.CreateThemeActivityView {

    companion object {
        private const val INTENT_TYPE_IMAGE = "image/*"
    }

    private lateinit var presenter: CreateThemeActivityContract.CreateThemeActivityPresenter

    private lateinit var saveButton: Button
    private lateinit var cancelButton: Button
    private lateinit var themeName: EditText
    private lateinit var productListPortraitBackground: ImageView
    private lateinit var productListLandscapeBackground: ImageView
    private lateinit var addProductPortraitBackground: ImageView
    private lateinit var addProductLandscapeBackground: ImageView
    private lateinit var productListPortraitBackgroundBorder: LinearLayout
    private lateinit var productListLandscapeBackgroundBorder: LinearLayout
    private lateinit var addProductPortraitBackgroundBorder: LinearLayout
    private lateinit var addProductLandscapeBackgroundBorder: LinearLayout

    private var productListPortraitBackgroundImage: ByteArray? = null
    private var productListLandscapeBackgroundImage: ByteArray? = null
    private var addProductPortraitBackgroundImage: ByteArray? = null
    private var addProductLandscapeBackgroundImage: ByteArray? = null

    private var productListPortraitBackgroundColor: Int? = null
    private var productListLandscapeBackgroundColor: Int? = null
    private var addProductPortraitBackgroundColor: Int? = null
    private var addProductLandscapeBackgroundColor: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_theme)
        presenter = CreateThemeActivityPresenter(this)
    }

    override fun initView() {
        saveButton = findViewById(R.id.button_save)
        cancelButton = findViewById(R.id.button_cancel)
        themeName = findViewById(R.id.create_theme_name)
        productListPortraitBackground = findViewById(R.id.create_theme_product_list_portrait_background)
        productListLandscapeBackground = findViewById(R.id.create_theme_product_list_landscape_background)
        addProductPortraitBackground = findViewById(R.id.create_theme_add_product_portrait_background)
        addProductLandscapeBackground = findViewById(R.id.create_theme_add_product_landscape_background)
        productListPortraitBackgroundBorder = findViewById(R.id.create_theme_product_list_portrait_background_border)
        productListLandscapeBackgroundBorder = findViewById(R.id.create_theme_product_list_landscape_background_border)
        addProductPortraitBackgroundBorder = findViewById(R.id.create_theme_add_product_portrait_background_border)
        addProductLandscapeBackgroundBorder = findViewById(R.id.create_theme_add_product_landscape_background_border)

        saveButton.setOnClickListener {
            saveTheme()
        }

        cancelButton.setOnClickListener {
            finish()
        }

        productListPortraitBackground.setOnClickListener {
            selectBackgroundType(BackgroundType.PRODUCT_LIST_PORTRAIT_BACKGROUND)
        }

        productListLandscapeBackground.setOnClickListener {
            selectBackgroundType(BackgroundType.PRODUCT_LIST_LANDSCAPE_BACKGROUND)
        }

        addProductPortraitBackground.setOnClickListener {
            selectBackgroundType(BackgroundType.ADD_PRODUCT_PORTRAIT_BACKGROUND)
        }

        addProductLandscapeBackground.setOnClickListener {
            selectBackgroundType(BackgroundType.ADD_PRODUCT_LANDSCAPE_BACKGROUND)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        setBackgroundSource(requestCode, resultCode, data)
    }

    private fun getInitialColor(backgroundType: BackgroundType): Int {
        val result: Int? = when (backgroundType) {
            BackgroundType.PRODUCT_LIST_PORTRAIT_BACKGROUND -> {
                productListPortraitBackgroundColor
            }
            BackgroundType.PRODUCT_LIST_LANDSCAPE_BACKGROUND -> {
                productListLandscapeBackgroundColor
            }
            BackgroundType.ADD_PRODUCT_PORTRAIT_BACKGROUND -> {
                addProductPortraitBackgroundColor
            }
            BackgroundType.ADD_PRODUCT_LANDSCAPE_BACKGROUND -> {
                addProductLandscapeBackgroundColor
            }
        }

        return result ?: ResourcesCompat.getColor(resources, R.color.sea_blue_dark, null)
    }

    private fun openColorPicker(backgroundType: BackgroundType) {
        AmbilWarnaDialog(this, getInitialColor(backgroundType), object : AmbilWarnaDialog.OnAmbilWarnaListener {
            override fun onCancel(dialog: AmbilWarnaDialog?) {}

            override fun onOk(dialog: AmbilWarnaDialog?, color: Int) {
                setBackgroundColor(backgroundType, color)
            }
        }).show()
    }

    private fun openGallery(backgroundType: BackgroundType) {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = INTENT_TYPE_IMAGE
        startActivityForResult(intent, backgroundType.typeId)
    }

    private fun removeBackground(backgroundType: BackgroundType) {
        var backgroundControl: ImageView? = null
        var border: LinearLayout? = null

        when (backgroundType) {
            BackgroundType.PRODUCT_LIST_PORTRAIT_BACKGROUND -> {
                productListPortraitBackgroundImage = null
                productListPortraitBackgroundColor = null
                backgroundControl = productListPortraitBackground
                border = productListPortraitBackgroundBorder
            }
            BackgroundType.PRODUCT_LIST_LANDSCAPE_BACKGROUND -> {
                productListLandscapeBackgroundImage = null
                productListLandscapeBackgroundColor = null
                backgroundControl = productListLandscapeBackground
                border = productListLandscapeBackgroundBorder
            }
            BackgroundType.ADD_PRODUCT_PORTRAIT_BACKGROUND -> {
                addProductPortraitBackgroundImage = null
                addProductPortraitBackgroundColor = null
                backgroundControl = addProductPortraitBackground
                border = addProductPortraitBackgroundBorder
            }
            BackgroundType.ADD_PRODUCT_LANDSCAPE_BACKGROUND -> {
                addProductLandscapeBackgroundImage = null
                addProductLandscapeBackgroundColor = null
                backgroundControl = addProductLandscapeBackground
                border = addProductLandscapeBackgroundBorder
            }
        }

        val background = ResourcesCompat.getDrawable(resources, R.drawable.ic_create_theme, null)
        backgroundControl.setImageDrawable(background)
        border.background?.setTint(ResourcesCompat.getColor(resources, R.color.transparent, null))
    }

    private fun saveTheme() {
        val name = themeName.text.toString()

        val result = presenter.saveTheme(
            name,
            productListPortraitBackgroundImage,
            productListLandscapeBackgroundImage,
            addProductPortraitBackgroundImage,
            addProductLandscapeBackgroundImage,
            productListPortraitBackgroundColor,
            productListLandscapeBackgroundColor,
            addProductPortraitBackgroundColor,
            addProductLandscapeBackgroundColor
        )

        when (result) {
            ValidationResult.EMPTY_NAME -> {
                Toast.makeText(this, resources.getString(R.string.empty_theme_name), Toast.LENGTH_LONG).show()
            }
            ValidationResult.MISSING_BACKGROUNDS -> {
                Toast.makeText(this, resources.getString(R.string.missing_backgrounds), Toast.LENGTH_LONG).show()
            }
            else -> finish()
        }
    }

    private fun selectBackgroundType(backgroundType: BackgroundType) {
        val backgroundTypePanel = BottomSheetDialog(this)
        val backgroundTypePanelView = LayoutInflater.from(this).inflate(R.layout.panel_background_type, findViewById(R.id.panel_background_type_container))

        backgroundTypePanelView.findViewById<LinearLayout>(R.id.panel_background_type_image).setOnClickListener {
            openGallery(backgroundType)
            backgroundTypePanel.dismiss()
        }

        backgroundTypePanelView.findViewById<LinearLayout>(R.id.panel_background_type_color).setOnClickListener {
            openColorPicker(backgroundType)
            backgroundTypePanel.dismiss()
        }

        var visible = View.GONE
        when (backgroundType) {
            BackgroundType.PRODUCT_LIST_PORTRAIT_BACKGROUND -> {
                if (productListPortraitBackgroundImage != null || productListPortraitBackgroundColor != null) {
                    visible = View.VISIBLE
                }
            }
            BackgroundType.PRODUCT_LIST_LANDSCAPE_BACKGROUND -> {
                if (productListLandscapeBackgroundImage != null || productListLandscapeBackgroundColor != null) {
                    visible = View.VISIBLE
                }
            }
            BackgroundType.ADD_PRODUCT_PORTRAIT_BACKGROUND -> {
                if (addProductPortraitBackgroundImage != null || addProductPortraitBackgroundColor != null) {
                    visible = View.VISIBLE
                }
            }
            BackgroundType.ADD_PRODUCT_LANDSCAPE_BACKGROUND -> {
                if (addProductLandscapeBackgroundImage != null || addProductLandscapeBackgroundColor != null) {
                    visible = View.VISIBLE
                }
            }
        }


        backgroundTypePanelView.findViewById<LinearLayout>(R.id.panel_background_type_remove).apply {
            visibility = visible
            if (visible == View.VISIBLE) {
                setOnClickListener {
                    removeBackground(backgroundType)
                    backgroundTypePanel.dismiss()
                }
            }
        }

        backgroundTypePanel.setContentView(backgroundTypePanelView)
        backgroundTypePanel.show()
    }

    private fun setBackgroundColor(backgroundType: BackgroundType, color: Int?) {
        when (backgroundType) {
            BackgroundType.PRODUCT_LIST_PORTRAIT_BACKGROUND -> {
                productListPortraitBackgroundColor = color
            }
            BackgroundType.PRODUCT_LIST_LANDSCAPE_BACKGROUND -> {
                productListLandscapeBackgroundColor = color
            }
            BackgroundType.ADD_PRODUCT_PORTRAIT_BACKGROUND -> {
                addProductPortraitBackgroundColor = color
            }
            BackgroundType.ADD_PRODUCT_LANDSCAPE_BACKGROUND -> {
                addProductLandscapeBackgroundColor = color
            }
        }

        if (color != null) setSelectedColor(backgroundType, color)
    }

    private fun setBackgroundSource(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK) {
            val uri = data?.data

            if (uri != null) {
                when (requestCode) {
                    BackgroundType.PRODUCT_LIST_PORTRAIT_BACKGROUND.typeId -> {
                        productListPortraitBackgroundImage = ImageUtils.getImageAsByteArray(this, uri)
                        productListPortraitBackground.setImageURI(uri)
                    }
                    BackgroundType.PRODUCT_LIST_LANDSCAPE_BACKGROUND.typeId -> {
                        productListLandscapeBackgroundImage = ImageUtils.getImageAsByteArray(this, uri)
                        productListLandscapeBackground.setImageURI(uri)
                    }
                    BackgroundType.ADD_PRODUCT_PORTRAIT_BACKGROUND.typeId -> {
                        addProductPortraitBackgroundImage = ImageUtils.getImageAsByteArray(this, uri)
                        addProductPortraitBackground.setImageURI(uri)
                    }
                    BackgroundType.ADD_PRODUCT_LANDSCAPE_BACKGROUND.typeId -> {
                        addProductLandscapeBackgroundImage = ImageUtils.getImageAsByteArray(this, uri)
                        addProductLandscapeBackground.setImageURI(uri)
                    }
                }
            }

            setBackgroundColor(BackgroundType.ADD_PRODUCT_LANDSCAPE_BACKGROUND, null)
        }
    }

    private fun setSelectedColor(backgroundType: BackgroundType, color: Int) {
        val imageView: ImageView
        val border: LinearLayout

        when (backgroundType) {
            BackgroundType.PRODUCT_LIST_PORTRAIT_BACKGROUND -> {
                imageView = productListPortraitBackground
                border = productListPortraitBackgroundBorder
            }
            BackgroundType.PRODUCT_LIST_LANDSCAPE_BACKGROUND -> {
                imageView = productListLandscapeBackground
                border = productListLandscapeBackgroundBorder
            }
            BackgroundType.ADD_PRODUCT_PORTRAIT_BACKGROUND -> {
                imageView = addProductPortraitBackground
                border = addProductPortraitBackgroundBorder
            }
            BackgroundType.ADD_PRODUCT_LANDSCAPE_BACKGROUND -> {
                imageView = addProductLandscapeBackground
                border = addProductLandscapeBackgroundBorder
            }
        }

        imageView.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.round_background, null))
        imageView.drawable.setTint(color)
        border.background.setTint(ResourcesCompat.getColor(resources, R.color.transparent_black, null))
    }
}

enum class BackgroundType(val typeId: Int) {
    PRODUCT_LIST_PORTRAIT_BACKGROUND(101),
    PRODUCT_LIST_LANDSCAPE_BACKGROUND(102),
    ADD_PRODUCT_PORTRAIT_BACKGROUND(103),
    ADD_PRODUCT_LANDSCAPE_BACKGROUND(104)
}