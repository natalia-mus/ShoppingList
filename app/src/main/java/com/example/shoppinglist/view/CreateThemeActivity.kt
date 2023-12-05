package com.example.shoppinglist.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.shoppinglist.R
import com.example.shoppinglist.contract.CreateThemeActivityContract
import com.example.shoppinglist.presenter.CreateThemeActivityPresenter

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

    private var productListPortraitBackgroundSource: Uri? = null
    private var productListLandscapeBackgroundSource: Uri? = null
    private var addProductPortraitBackgroundSource: Uri? = null
    private var addProductLandscapeBackgroundSource: Uri? = null

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

        saveButton.setOnClickListener {
            saveTheme()
        }

        cancelButton.setOnClickListener {
            finish()
        }

        productListPortraitBackground.setOnClickListener {
            openGallery(BackgroundType.PRODUCT_LIST_PORTRAIT_BACKGROUND)
        }

        productListLandscapeBackground.setOnClickListener {
            openGallery(BackgroundType.PRODUCT_LIST_LANDSCAPE_BACKGROUND)
        }

        addProductPortraitBackground.setOnClickListener {
            openGallery(BackgroundType.ADD_PRODUCT_PORTRAIT_BACKGROUND)
        }

        addProductLandscapeBackground.setOnClickListener {
            openGallery(BackgroundType.ADD_PRODUCT_LANDSCAPE_BACKGROUND)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        setBackgroundSource(requestCode, resultCode, data)
    }

    private fun openGallery(backgroundType: BackgroundType) {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = INTENT_TYPE_IMAGE
        startActivityForResult(intent, backgroundType.typeId)
    }

    private fun saveTheme() {
        val name = themeName.text.toString()

        presenter.saveTheme(
            name,
            productListPortraitBackgroundSource.toString(),
            productListLandscapeBackgroundSource.toString(),
            addProductPortraitBackgroundSource.toString(),
            addProductLandscapeBackgroundSource.toString()
        )
    }

    private fun setBackgroundSource(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK) {
            val uri = data?.data

            if (uri != null) {
                when (requestCode) {
                    BackgroundType.PRODUCT_LIST_PORTRAIT_BACKGROUND.typeId -> {
                        productListPortraitBackgroundSource = uri
                        productListPortraitBackground.setImageURI(uri)
                    }
                    BackgroundType.PRODUCT_LIST_LANDSCAPE_BACKGROUND.typeId -> {
                        productListLandscapeBackgroundSource = uri
                        productListLandscapeBackground.setImageURI(uri)
                    }
                    BackgroundType.ADD_PRODUCT_PORTRAIT_BACKGROUND.typeId -> {
                        addProductPortraitBackgroundSource = uri
                        addProductPortraitBackground.setImageURI(uri)
                    }
                    BackgroundType.ADD_PRODUCT_LANDSCAPE_BACKGROUND.typeId -> {
                        addProductLandscapeBackgroundSource = uri
                        addProductLandscapeBackground.setImageURI(uri)
                    }
                }
            }
        }
    }
}

enum class BackgroundType(val typeId: Int) {
    PRODUCT_LIST_PORTRAIT_BACKGROUND(101),
    PRODUCT_LIST_LANDSCAPE_BACKGROUND(102),
    ADD_PRODUCT_PORTRAIT_BACKGROUND(103),
    ADD_PRODUCT_LANDSCAPE_BACKGROUND(104)
}