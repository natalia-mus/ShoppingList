package com.example.shoppinglist.view

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.res.ResourcesCompat
import com.example.shoppinglist.ImageUtils
import com.example.shoppinglist.R
import com.example.shoppinglist.ValidationResult
import com.example.shoppinglist.contract.CreateThemeActivityContract
import com.example.shoppinglist.model.Icon
import com.example.shoppinglist.presenter.CreateThemeActivityPresenter
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.slider.Slider
import kotlinx.android.synthetic.main.product_item.*
import yuku.ambilwarna.AmbilWarnaDialog

class CreateThemeActivity : AppCompatActivity(), CreateThemeActivityContract.CreateThemeActivityView {

    companion object {
        private const val DEFAULT_BACKGROUND_COLOR = Color.BLACK
        private const val DEFAULT_BACKGROUND_ALFA = 0.3f
        private const val DEFAULT_TEXT_COLOR = Color.WHITE
        private const val INTENT_TYPE_IMAGE = "image/*"
    }

    private lateinit var presenter: CreateThemeActivityContract.CreateThemeActivityPresenter

    private lateinit var saveButton: Button
    private lateinit var cancelButton: Button
    private lateinit var nextButton: Button
    private lateinit var themeName: EditText
    private lateinit var productListPortraitBackground: ImageView
    private lateinit var productListLandscapeBackground: ImageView
    private lateinit var addProductPortraitBackground: ImageView
    private lateinit var addProductLandscapeBackground: ImageView
    private lateinit var productListPortraitBackgroundBorder: LinearLayout
    private lateinit var productListLandscapeBackgroundBorder: LinearLayout
    private lateinit var addProductPortraitBackgroundBorder: LinearLayout
    private lateinit var addProductLandscapeBackgroundBorder: LinearLayout
    private lateinit var productItemNameLabel: TextView
    private lateinit var productItemQuantityLabel: TextView
    private lateinit var productItemPriorityLabel: TextView
    private lateinit var productItemQuantityValue: TextView
    private lateinit var productItemPriorityValue: TextView
    private lateinit var productItemButtonDelete: ImageButton
    private lateinit var productItemBackground: LinearLayout
    private lateinit var iconTrashBin: LinearLayout
    private lateinit var iconCross: LinearLayout
    private lateinit var boldProductNameSwitch: SwitchCompat
    private lateinit var backgroundTransparencySlider: Slider
    private lateinit var productItemBackgroundColor: ImageView
    private lateinit var productItemBackgroundColorBorder: LinearLayout
    private lateinit var productItemTextColor: ImageView
    private lateinit var productItemTextColorBorder: LinearLayout
    private lateinit var deleteIconColor: ImageView
    private lateinit var deleteIconColorBorder: LinearLayout


    private val backgroundTransparencySliderValueChangedListener = object : Slider.OnChangeListener {
        @SuppressLint("RestrictedApi")
        override fun onValueChange(slider: Slider, value: Float, fromUser: Boolean) {
            setProductItemBackgroundAlpha(value)
        }
    }

    private val boldProductNameOnCheckedChangedListener = object : CompoundButton.OnCheckedChangeListener {
        override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
            setBoldProductName(isChecked)
        }
    }

    private val creatorSteps = ArrayList<Int>()

    private var productListPortraitBackgroundImage: ByteArray? = null
    private var productListLandscapeBackgroundImage: ByteArray? = null
    private var addProductPortraitBackgroundImage: ByteArray? = null
    private var addProductLandscapeBackgroundImage: ByteArray? = null

    private var productListPortraitBackgroundColor: Int? = null
    private var productListLandscapeBackgroundColor: Int? = null
    private var addProductPortraitBackgroundColor: Int? = null
    private var addProductLandscapeBackgroundColor: Int? = null

    private var name = ""
    private var deleteIcon = Icon.TRASH_BIN
    private var boldProductName = true
    private var productItemBackgroundAlphaValue: String? = ""
    private var productItemBackgroundColorValue: String? = ""
    private var productItemTextColorValue: Int = DEFAULT_TEXT_COLOR
    private var deleteIconColorValue: Int? = null

    private var currentCreatorStep = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_theme_first_step)
        presenter = CreateThemeActivityPresenter(this)
        populateCreatorStepsList()
    }

    override fun initView() {
        nextButton = findViewById(R.id.create_theme_next)
        productListPortraitBackground = findViewById(R.id.create_theme_product_list_portrait_background)
        productListLandscapeBackground = findViewById(R.id.create_theme_product_list_landscape_background)
        addProductPortraitBackground = findViewById(R.id.create_theme_add_product_portrait_background)
        addProductLandscapeBackground = findViewById(R.id.create_theme_add_product_landscape_background)
        productListPortraitBackgroundBorder = findViewById(R.id.create_theme_product_list_portrait_background_border)
        productListLandscapeBackgroundBorder = findViewById(R.id.create_theme_product_list_landscape_background_border)
        addProductPortraitBackgroundBorder = findViewById(R.id.create_theme_add_product_portrait_background_border)
        addProductLandscapeBackgroundBorder = findViewById(R.id.create_theme_add_product_landscape_background_border)

        nextButton.setOnClickListener {
            nextStep()
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

    private fun changeView() {
        setContentView(creatorSteps[currentCreatorStep])

        when (creatorSteps[currentCreatorStep]) {
            R.layout.activity_create_theme_second_step -> initSecondStepView()
            R.layout.activity_create_theme_last_step -> initLastStepView()
        }
    }

    private fun getInitialColor(elementType: ElementType): Int {
        val result: Int? = when (elementType) {
            ElementType.PRODUCT_LIST_PORTRAIT_BACKGROUND -> {
                productListPortraitBackgroundColor
            }
            ElementType.PRODUCT_LIST_LANDSCAPE_BACKGROUND -> {
                productListLandscapeBackgroundColor
            }
            ElementType.ADD_PRODUCT_PORTRAIT_BACKGROUND -> {
                addProductPortraitBackgroundColor
            }
            ElementType.ADD_PRODUCT_LANDSCAPE_BACKGROUND -> {
                addProductLandscapeBackgroundColor
            }
            ElementType.PRODUCT_ITEM_BACKGROUND_COLOR -> {
                Color.parseColor("#$productItemBackgroundColorValue")
            }
            ElementType.PRODUCT_ITEM_TEXT_COLOR -> {
                productItemTextColorValue
            }
            ElementType.DELETE_ICON_COLOR -> {
                deleteIconColorValue
            }
        }

        return result ?: ResourcesCompat.getColor(resources, R.color.sea_blue_dark, null)
    }

    private fun initSecondStepView() {
        productItemBackground = findViewById(R.id.create_theme_product_item_visualization)
        productItemNameLabel = findViewById(R.id.product_name)
        productItemQuantityLabel = findViewById(R.id.product_quantity_label)
        productItemPriorityLabel = findViewById(R.id.product_priority_label)
        productItemQuantityValue = findViewById(R.id.product_quantity)
        productItemPriorityValue = findViewById(R.id.product_priority)
        productItemButtonDelete = findViewById(R.id.product_button_delete)
        iconTrashBin = findViewById(R.id.create_theme_icon_trash_bin)
        iconCross = findViewById(R.id.create_theme_icon_cross)
        boldProductNameSwitch = findViewById(R.id.create_theme_bold_switch)
        backgroundTransparencySlider = findViewById(R.id.create_theme_product_background_transparency)
        productItemBackgroundColor = findViewById(R.id.create_theme_background_color)
        productItemBackgroundColorBorder = findViewById(R.id.create_theme_background_color_border)
        productItemTextColor = findViewById(R.id.create_theme_text_color)
        productItemTextColorBorder = findViewById(R.id.create_theme_text_color_border)
        deleteIconColor = findViewById(R.id.create_theme_delete_icon_color)
        deleteIconColorBorder = findViewById(R.id.create_theme_delete_icon_color_border)
        nextButton = findViewById(R.id.create_theme_next)

        boldProductNameSwitch.setOnCheckedChangeListener(boldProductNameOnCheckedChangedListener)

        backgroundTransparencySlider.addOnChangeListener(backgroundTransparencySliderValueChangedListener)

        productItemBackgroundColor.setOnClickListener {
            openColorPicker(ElementType.PRODUCT_ITEM_BACKGROUND_COLOR)
        }

        productItemTextColor.setOnClickListener {
            openColorPicker(ElementType.PRODUCT_ITEM_TEXT_COLOR)
        }

        deleteIconColor.setOnClickListener {
            openColorPicker(ElementType.DELETE_ICON_COLOR)
        }

        iconTrashBin.setOnClickListener {
            selectIcon(Icon.TRASH_BIN)
        }

        iconCross.setOnClickListener {
            selectIcon(Icon.CROSS)
        }

        boldProductNameSwitch.setOnClickListener {
            boldProductName = !boldProductName
        }

        nextButton.setOnClickListener {
            nextStep()
        }

        val background = findViewById<ScrollView>(R.id.create_theme_second_step)
        setVisualizationBackground(background)
        setProductItemBackgroundColor(DEFAULT_BACKGROUND_COLOR)
        setProductItemBackgroundAlpha(DEFAULT_BACKGROUND_ALFA)
        setColor(ElementType.PRODUCT_ITEM_BACKGROUND_COLOR, DEFAULT_BACKGROUND_COLOR)
        setProductItemTextColor(DEFAULT_TEXT_COLOR)
        setColor(ElementType.PRODUCT_ITEM_TEXT_COLOR, DEFAULT_TEXT_COLOR)
        getDeleteIconColorValue()
        setColor(ElementType.DELETE_ICON_COLOR, deleteIconColorValue)
        backgroundTransparencySlider.value = DEFAULT_BACKGROUND_ALFA
    }

    private fun getDeleteIconColorValue(): Int {
        if (deleteIconColorValue == null) {
            deleteIconColorValue = ResourcesCompat.getColor(resources, R.color.sea_blue_light, null)
        }
        return deleteIconColorValue!!
    }

    private fun setDeleteIconColor(color: Int) {
        deleteIconColorValue = color
        productItemButtonDelete.background.setTint(color)
    }

    private fun setProductItemBackground() {
        if (productItemBackgroundColorValue != null) {
            val colorValue = getProductItemBackgroundValue()
            productItemBackground.background.setTint(Color.parseColor(colorValue))
        }
    }

    private fun getProductItemBackgroundValue() = "#$productItemBackgroundAlphaValue$productItemBackgroundColorValue"

    private fun setProductItemTextColor(color: Int) {
        productItemTextColorValue = color
        productItemNameLabel.setTextColor(color)
        productItemQuantityLabel.setTextColor(color)
        productItemPriorityLabel.setTextColor(color)
        productItemQuantityValue.setTextColor(color)
        productItemPriorityValue.setTextColor(color)
    }

    private fun setProductItemBackgroundColor(color: Int) {
        val colorValue = Integer.toHexString(color)
        val alpha = if (colorValue.length == 8) 2 else 0
        productItemBackgroundColorValue = colorValue.drop(alpha)
        setProductItemBackground()
    }

    private fun setProductItemBackgroundAlpha(alphaPercentage: Float) {
        val alpha = (255 * alphaPercentage).toInt()
        productItemBackgroundAlphaValue = Integer.toHexString(alpha)

        if (productItemBackgroundAlphaValue?.length == 1) {
            productItemBackgroundAlphaValue = "0$productItemBackgroundAlphaValue"
        }

        setProductItemBackground()
    }

    private fun initLastStepView() {
        saveButton = findViewById(R.id.button_save)
        cancelButton = findViewById(R.id.button_cancel)
        themeName = findViewById(R.id.create_theme_name)

        saveButton.setOnClickListener {
            if (validateCurrentStep()) {
                saveTheme()
            }
        }

        cancelButton.setOnClickListener {
            finish()
        }
    }

    private fun nextStep() {
        if (validateCurrentStep()) {
            currentCreatorStep++
            changeView()
        }
    }

    private fun openColorPicker(elementType: ElementType) {
        AmbilWarnaDialog(this, getInitialColor(elementType), object : AmbilWarnaDialog.OnAmbilWarnaListener {
            override fun onCancel(dialog: AmbilWarnaDialog?) {}

            override fun onOk(dialog: AmbilWarnaDialog?, color: Int) {
                setColor(elementType, color)
            }
        }).show()
    }

    private fun openGallery(backgroundType: BackgroundType) {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = INTENT_TYPE_IMAGE
        startActivityForResult(intent, backgroundType.backgroundTypeId)
    }

    private fun populateCreatorStepsList() {
        creatorSteps.add(0, R.layout.activity_create_theme_first_step)
        creatorSteps.add(1, R.layout.activity_create_theme_second_step)
        creatorSteps.add(2, R.layout.activity_create_theme_last_step)
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
        presenter.saveTheme(
            name,
            productListPortraitBackgroundImage,
            productListLandscapeBackgroundImage,
            addProductPortraitBackgroundImage,
            addProductLandscapeBackgroundImage,
            productListPortraitBackgroundColor,
            productListLandscapeBackgroundColor,
            addProductPortraitBackgroundColor,
            addProductLandscapeBackgroundColor,
            getProductItemBackgroundValue(),
            productItemTextColorValue,
            getDeleteIconColorValue(),
            deleteIcon,
            boldProductName
        )

        finish()
    }

    private fun selectBackgroundType(backgroundType: BackgroundType) {
        val backgroundTypePanel = BottomSheetDialog(this)
        val backgroundTypePanelView = LayoutInflater.from(this).inflate(R.layout.panel_background_type, findViewById(R.id.panel_background_type_container))

        backgroundTypePanelView.findViewById<LinearLayout>(R.id.panel_background_type_image).setOnClickListener {
            openGallery(backgroundType)
            backgroundTypePanel.dismiss()
        }

        backgroundTypePanelView.findViewById<LinearLayout>(R.id.panel_background_type_color).setOnClickListener {
            val elementType = ElementType.getByElementTypeId(backgroundType.backgroundTypeId)
            if (elementType != null) {
                openColorPicker(elementType)
            }
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

    private fun selectIcon(icon: Icon) {
        deleteIcon = icon

        val iconToSelect: LinearLayout
        val iconToUnselect: LinearLayout
        val iconDrawable: Drawable?

        when (icon) {
            Icon.TRASH_BIN -> {
                iconToSelect = iconTrashBin
                iconToUnselect = iconCross
                iconDrawable = ResourcesCompat.getDrawable(resources, R.drawable.ic_baseline_delete_24, null)
            }
            Icon.CROSS -> {
                iconToSelect = iconCross
                iconToUnselect = iconTrashBin
                iconDrawable = ResourcesCompat.getDrawable(resources, R.drawable.ic_cancel, null)
            }
        }

        iconToSelect.background.setTint(ResourcesCompat.getColor(resources, R.color.transparent_white, null))
        iconToUnselect.background.setTint(ResourcesCompat.getColor(resources, R.color.transparent, null))
        productItemButtonDelete.background = iconDrawable
        setColor(ElementType.DELETE_ICON_COLOR, deleteIconColorValue)
    }

    private fun setColor(elementType: ElementType, color: Int?) {
        when (elementType) {
            ElementType.PRODUCT_LIST_PORTRAIT_BACKGROUND -> {
                productListPortraitBackgroundColor = color
            }
            ElementType.PRODUCT_LIST_LANDSCAPE_BACKGROUND -> {
                productListLandscapeBackgroundColor = color
            }
            ElementType.ADD_PRODUCT_PORTRAIT_BACKGROUND -> {
                addProductPortraitBackgroundColor = color
            }
            ElementType.ADD_PRODUCT_LANDSCAPE_BACKGROUND -> {
                addProductLandscapeBackgroundColor = color
            }
            ElementType.PRODUCT_ITEM_BACKGROUND_COLOR -> {
                if (color != null) {
                    setProductItemBackgroundColor(color)
                }
            }
            ElementType.PRODUCT_ITEM_TEXT_COLOR -> {
                if (color != null) {
                    setProductItemTextColor(color)
                }
            }
            ElementType.DELETE_ICON_COLOR -> {
                if (color != null) {
                    setDeleteIconColor(color)
                }
            }
        }

        if (color != null) setSelectedColor(elementType, color)
    }

    private fun setBackgroundSource(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK) {
            val uri = data?.data

            if (uri != null) {
                when (requestCode) {
                    ElementType.PRODUCT_LIST_PORTRAIT_BACKGROUND.elementTypeId -> {
                        productListPortraitBackgroundImage = ImageUtils.getImageAsByteArray(this, uri)
                        productListPortraitBackground.setImageURI(uri)
                    }
                    ElementType.PRODUCT_LIST_LANDSCAPE_BACKGROUND.elementTypeId -> {
                        productListLandscapeBackgroundImage = ImageUtils.getImageAsByteArray(this, uri)
                        productListLandscapeBackground.setImageURI(uri)
                    }
                    ElementType.ADD_PRODUCT_PORTRAIT_BACKGROUND.elementTypeId -> {
                        addProductPortraitBackgroundImage = ImageUtils.getImageAsByteArray(this, uri)
                        addProductPortraitBackground.setImageURI(uri)
                    }
                    ElementType.ADD_PRODUCT_LANDSCAPE_BACKGROUND.elementTypeId -> {
                        addProductLandscapeBackgroundImage = ImageUtils.getImageAsByteArray(this, uri)
                        addProductLandscapeBackground.setImageURI(uri)
                    }
                }
            }

            setColor(ElementType.ADD_PRODUCT_LANDSCAPE_BACKGROUND, null)
        }
    }

    private fun setBoldProductName(bold: Boolean) {
        if (bold) {
            productItemNameLabel.typeface = Typeface.DEFAULT_BOLD
        } else {
            productItemNameLabel.typeface = Typeface.DEFAULT
        }
    }

    private fun setSelectedColor(backgroundType: ElementType, color: Int) {
        val imageView: ImageView
        val border: LinearLayout

        when (backgroundType) {
            ElementType.PRODUCT_LIST_PORTRAIT_BACKGROUND -> {
                imageView = productListPortraitBackground
                border = productListPortraitBackgroundBorder
            }
            ElementType.PRODUCT_LIST_LANDSCAPE_BACKGROUND -> {
                imageView = productListLandscapeBackground
                border = productListLandscapeBackgroundBorder
            }
            ElementType.ADD_PRODUCT_PORTRAIT_BACKGROUND -> {
                imageView = addProductPortraitBackground
                border = addProductPortraitBackgroundBorder
            }
            ElementType.ADD_PRODUCT_LANDSCAPE_BACKGROUND -> {
                imageView = addProductLandscapeBackground
                border = addProductLandscapeBackgroundBorder
            }
            ElementType.PRODUCT_ITEM_BACKGROUND_COLOR -> {
                imageView = productItemBackgroundColor
                border = productItemBackgroundColorBorder
            }
            ElementType.PRODUCT_ITEM_TEXT_COLOR -> {
                imageView = productItemTextColor
                border = productItemTextColorBorder
            }
            ElementType.DELETE_ICON_COLOR -> {
                imageView = deleteIconColor
                border = deleteIconColorBorder
            }
        }

        imageView.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.round_background, null))
        imageView.drawable.setTint(color)
        border.background.setTint(ResourcesCompat.getColor(resources, R.color.transparent_black, null))
    }

    private fun setVisualizationBackground(view: View) {
        val orientation = resources.configuration.orientation
        var backgroundImage: ByteArray? = null
        var backgroundColor: Int? = null

        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            if (addProductPortraitBackgroundImage != null) {
                backgroundImage = addProductPortraitBackgroundImage

            } else if (addProductPortraitBackgroundColor != null) {
                backgroundColor = addProductPortraitBackgroundColor

            } else {
                backgroundImage = ImageUtils.getImageAsByteArray(ResourcesCompat.getDrawable(resources, R.drawable.theme_grocery_list_portrait, null))
            }

        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            if (addProductLandscapeBackgroundImage != null) {
                backgroundImage = addProductLandscapeBackgroundImage

            } else if (addProductLandscapeBackgroundColor != null) {
                backgroundColor = addProductLandscapeBackgroundColor

            } else {
                backgroundImage = ImageUtils.getImageAsByteArray(ResourcesCompat.getDrawable(resources, R.drawable.theme_grocery_list_portrait, null))
            }
        }

        if (backgroundImage != null) {
            val background = ImageUtils.getImageAsDrawable(this, backgroundImage)
            view.background = background

        } else if (backgroundColor != null) {
            view.setBackgroundColor(backgroundColor)
        }
    }

    /**
     * @return true if validation success, false otherwise
     */
    private fun validateCurrentStep(): Boolean {
        val validationResult = when (creatorSteps[currentCreatorStep]) {
            R.layout.activity_create_theme_first_step -> presenter.validateFirstStep()
            R.layout.activity_create_theme_second_step -> presenter.validateSecondStep(
                productListPortraitBackgroundImage,
                productListLandscapeBackgroundImage,
                addProductPortraitBackgroundImage,
                addProductLandscapeBackgroundImage,
                productListPortraitBackgroundColor,
                productListLandscapeBackgroundColor,
                addProductPortraitBackgroundColor,
                addProductLandscapeBackgroundColor,
                getProductItemBackgroundValue(),
                productItemTextColorValue,
                getDeleteIconColorValue(),
                deleteIcon,
                boldProductName
            )
            R.layout.activity_create_theme_last_step -> {
                name = themeName.text.toString()
                presenter.validateLastStep(name)
            }
            else -> true
        }

        return when (validationResult) {
            ValidationResult.EMPTY_NAME -> {
                Toast.makeText(this, resources.getString(R.string.empty_theme_name), Toast.LENGTH_LONG).show()
                false
            }
            ValidationResult.NO_DIFFERENCE -> {
                Toast.makeText(this, resources.getString(R.string.no_difference), Toast.LENGTH_LONG).show()
                false
            }
            else -> true
        }
    }
}

private enum class BackgroundType(val backgroundTypeId: Int) {
    PRODUCT_LIST_PORTRAIT_BACKGROUND(101),
    PRODUCT_LIST_LANDSCAPE_BACKGROUND(102),
    ADD_PRODUCT_PORTRAIT_BACKGROUND(103),
    ADD_PRODUCT_LANDSCAPE_BACKGROUND(104)
}

private enum class ElementType(val elementTypeId: Int) {
    PRODUCT_LIST_PORTRAIT_BACKGROUND(101),
    PRODUCT_LIST_LANDSCAPE_BACKGROUND(102),
    ADD_PRODUCT_PORTRAIT_BACKGROUND(103),
    ADD_PRODUCT_LANDSCAPE_BACKGROUND(104),
    PRODUCT_ITEM_BACKGROUND_COLOR(105),
    PRODUCT_ITEM_TEXT_COLOR(106),
    DELETE_ICON_COLOR(107);

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