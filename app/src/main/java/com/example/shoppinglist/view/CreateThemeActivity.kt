package com.example.shoppinglist.view

import android.annotation.SuppressLint
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
import androidx.core.graphics.alpha
import com.example.shoppinglist.ImageUtils
import com.example.shoppinglist.R
import com.example.shoppinglist.ValidationResult
import com.example.shoppinglist.contract.CreateThemeActivityContract
import com.example.shoppinglist.control.ImageColorPicker
import com.example.shoppinglist.model.Icon
import com.example.shoppinglist.presenter.CreateThemeActivityPresenter
import com.google.android.material.slider.Slider
import kotlinx.android.synthetic.main.product_item.*

class CreateThemeActivity : AppCompatActivity(), CreateThemeActivityContract.CreateThemeActivityView {

    companion object {
        private const val DEFAULT_BACKGROUND_COLOR = Color.BLACK
        private const val DEFAULT_BACKGROUND_ALFA = 0.3f
    }

    private lateinit var presenter: CreateThemeActivityContract.CreateThemeActivityPresenter

    private lateinit var saveButton: Button
    private lateinit var cancelButton: Button
    private lateinit var nextButton: Button
    private lateinit var themeName: EditText
    private lateinit var productListPortraitBackground: ImageColorPicker
    private lateinit var productListLandscapeBackground: ImageColorPicker
    private lateinit var addProductPortraitBackground: ImageColorPicker
    private lateinit var addProductLandscapeBackground: ImageColorPicker
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
    private lateinit var productItemBackgroundColor: ImageColorPicker
    private lateinit var productItemTextColor: ImageColorPicker
    private lateinit var deleteIconColor: ImageColorPicker


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

    private val onImageColorSetListener = object : ImageColorPicker.OnImageColorSetListener {
        override fun onCopyImage(elementType: ElementType?) {
            copyBackground(elementType)
        }

        override fun onColorSet(elementType: ElementType?, color: Int) {
            setColor(elementType, color)
            setCopyOptionVisibility(elementType, true)
        }

        override fun onImageSet(elementType: ElementType?, image: ByteArray) {
            if (elementType != null) {
                setImage(elementType, image)
                setCopyOptionVisibility(elementType, true)
            }
        }

        override fun onRemoveImage(elementType: ElementType?) {
            removeBackground(elementType)
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
    private var productItemTextColorValue: Int? = null
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

        nextButton.setOnClickListener {
            nextStep()
        }

        productListPortraitBackground.setOnImageColorSetListener(onImageColorSetListener)
        productListLandscapeBackground.setOnImageColorSetListener(onImageColorSetListener)
        addProductPortraitBackground.setOnImageColorSetListener(onImageColorSetListener)
        addProductLandscapeBackground.setOnImageColorSetListener(onImageColorSetListener)
    }

    private fun changeView() {
        setContentView(creatorSteps[currentCreatorStep])

        when (creatorSteps[currentCreatorStep]) {
            R.layout.activity_create_theme_second_step -> initSecondStepView()
            R.layout.activity_create_theme_last_step -> initLastStepView()
        }
    }

    private fun copyBackground(elementType: ElementType?) {
        when (elementType) {
            ElementType.PRODUCT_LIST_PORTRAIT_BACKGROUND -> {
                removeBackground(ElementType.PRODUCT_LIST_PORTRAIT_BACKGROUND)
                if (productListLandscapeBackgroundImage != null) {
                    setImage(ElementType.PRODUCT_LIST_LANDSCAPE_BACKGROUND, productListLandscapeBackgroundImage!!)
                    productListPortraitBackground.setSelectedImage(productListLandscapeBackgroundImage)

                } else if (productListLandscapeBackgroundColor != null) {
                    setColor(ElementType.PRODUCT_LIST_LANDSCAPE_BACKGROUND, productListLandscapeBackgroundColor)
                    productListPortraitBackground.setSelectedColor(productListLandscapeBackgroundColor!!)
                }
            }
            ElementType.PRODUCT_LIST_LANDSCAPE_BACKGROUND -> {
                removeBackground(ElementType.PRODUCT_LIST_LANDSCAPE_BACKGROUND)
                if (productListPortraitBackgroundImage != null) {
                    setImage(ElementType.PRODUCT_LIST_PORTRAIT_BACKGROUND, productListPortraitBackgroundImage!!)
                    productListLandscapeBackground.setSelectedImage(productListPortraitBackgroundImage)

                } else if (productListPortraitBackgroundColor != null) {
                    setColor(ElementType.PRODUCT_LIST_PORTRAIT_BACKGROUND, productListPortraitBackgroundColor)
                    productListLandscapeBackground.setSelectedColor(productListPortraitBackgroundColor!!)
                }
            }
            ElementType.ADD_PRODUCT_PORTRAIT_BACKGROUND -> {
                removeBackground(ElementType.ADD_PRODUCT_PORTRAIT_BACKGROUND)
                if (addProductLandscapeBackgroundImage != null) {
                    setImage(ElementType.ADD_PRODUCT_LANDSCAPE_BACKGROUND, addProductLandscapeBackgroundImage!!)
                    addProductPortraitBackground.setSelectedImage(addProductLandscapeBackgroundImage)

                } else if (addProductLandscapeBackgroundColor != null) {
                    setColor(ElementType.ADD_PRODUCT_LANDSCAPE_BACKGROUND, addProductLandscapeBackgroundColor)
                    addProductPortraitBackground.setSelectedColor(addProductLandscapeBackgroundColor!!)
                }
            }
            ElementType.ADD_PRODUCT_LANDSCAPE_BACKGROUND -> {
                removeBackground(ElementType.ADD_PRODUCT_LANDSCAPE_BACKGROUND)
                if (addProductPortraitBackgroundImage != null) {
                    setImage(ElementType.ADD_PRODUCT_PORTRAIT_BACKGROUND, addProductPortraitBackgroundImage!!)
                    addProductLandscapeBackground.setSelectedImage(addProductPortraitBackgroundImage)

                } else if (addProductPortraitBackgroundColor != null) {
                    setColor(ElementType.ADD_PRODUCT_PORTRAIT_BACKGROUND, addProductPortraitBackgroundColor)
                    addProductLandscapeBackground.setSelectedColor(addProductPortraitBackgroundColor!!)
                }
            }
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
        productItemTextColor = findViewById(R.id.create_theme_text_color)
        deleteIconColor = findViewById(R.id.create_theme_delete_icon_color)
        nextButton = findViewById(R.id.create_theme_next)

        boldProductNameSwitch.setOnCheckedChangeListener(boldProductNameOnCheckedChangedListener)

        backgroundTransparencySlider.addOnChangeListener(backgroundTransparencySliderValueChangedListener)

        productItemBackgroundColor.setOnImageColorSetListener(onImageColorSetListener)
        productItemTextColor.setOnImageColorSetListener(onImageColorSetListener)
        deleteIconColor.setOnImageColorSetListener(onImageColorSetListener)

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

        setDefaultColors()

        val background = findViewById<ScrollView>(R.id.create_theme_second_step)
        setVisualizationBackground(background)
        backgroundTransparencySlider.value = DEFAULT_BACKGROUND_ALFA
    }

    private fun getDeleteIconColorValue(): Int {
        if (deleteIconColorValue == null) {
            setDefaultDeleteIconColor()
        }
        return deleteIconColorValue!!
    }

    private fun getProductItemTextColorValue(): Int {
        if (productItemTextColorValue == null) {
            setDefaultProductItemTextColor()
        }
        return productItemTextColorValue!!
    }

    private fun setDeleteIconColor(color: Int) {
        deleteIconColorValue = color
        productItemButtonDelete.background.setTint(color)
    }

    private fun setProductItemBackground() {
        val colorValue = getProductItemBackgroundValue()
        productItemBackground.background.setTint(Color.parseColor(colorValue))
    }

    private fun getProductItemBackgroundValue(): String {
        if (productItemBackgroundAlphaValue == null || productItemBackgroundColorValue == null) {
            setDefaultProductItemBackgroundValue()
        }

        return "#$productItemBackgroundAlphaValue$productItemBackgroundColorValue"
    }

    private fun setDefaultProductItemBackgroundValue() {
        val defaultProductItemBackgroundValue = presenter.getDefaultProductItemBackgroundValue()

        defaultProductItemBackgroundValue?.let {
            val color = Color.parseColor(it)
            val colorWithoutAlpha = Color.rgb(Color.red(color), Color.green(color), Color.blue(color))

            productItemBackgroundColor.setSelectedColor(colorWithoutAlpha)
            setProductItemBackgroundColor(color)
            setProductItemBackgroundAlpha(color.alpha)
        }
    }

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
        setProductItemBackgroundAlpha(alpha)
    }

    private fun setProductItemBackgroundAlpha(alpha: Int) {
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

    private fun populateCreatorStepsList() {
        creatorSteps.add(0, R.layout.activity_create_theme_first_step)
        creatorSteps.add(1, R.layout.activity_create_theme_second_step)
        creatorSteps.add(2, R.layout.activity_create_theme_last_step)
    }

    private fun removeBackground(backgroundType: ElementType?) {
        when (backgroundType) {
            ElementType.PRODUCT_LIST_PORTRAIT_BACKGROUND -> {
                productListPortraitBackgroundColor = null
                productListPortraitBackgroundImage = null
                productListPortraitBackground.removeBackground()
                productListLandscapeBackground.showCopyOption(false)
            }
            ElementType.PRODUCT_LIST_LANDSCAPE_BACKGROUND -> {
                productListLandscapeBackgroundColor = null
                productListLandscapeBackgroundImage = null
                productListLandscapeBackground.removeBackground()
                productListPortraitBackground.showCopyOption(false)
            }
            ElementType.ADD_PRODUCT_PORTRAIT_BACKGROUND -> {
                addProductPortraitBackgroundColor = null
                addProductPortraitBackgroundImage = null
                addProductPortraitBackground.removeBackground()
                addProductLandscapeBackground.showCopyOption(false)
            }
            ElementType.ADD_PRODUCT_LANDSCAPE_BACKGROUND -> {
                addProductLandscapeBackgroundColor = null
                addProductLandscapeBackgroundImage = null
                addProductLandscapeBackground.removeBackground()
                addProductPortraitBackground.showCopyOption(false)
            }
        }
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
            getProductItemTextColorValue(),
            getDeleteIconColorValue(),
            deleteIcon,
            boldProductName
        )

        finish()
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
                iconDrawable = ResourcesCompat.getDrawable(resources, R.drawable.ic_trash_bin, null)
            }
            Icon.CROSS -> {
                iconToSelect = iconCross
                iconToUnselect = iconTrashBin
                iconDrawable = ResourcesCompat.getDrawable(resources, R.drawable.ic_cross, null)
            }
        }

        iconToSelect.background.setTint(ResourcesCompat.getColor(resources, R.color.transparent_white, null))
        iconToUnselect.background.setTint(ResourcesCompat.getColor(resources, R.color.transparent, null))
        productItemButtonDelete.background = iconDrawable
        setColor(ElementType.DELETE_ICON_COLOR, deleteIconColorValue)
    }

    private fun setColor(elementType: ElementType?, color: Int?) {
        when (elementType) {
            ElementType.PRODUCT_LIST_PORTRAIT_BACKGROUND -> {
                productListPortraitBackgroundColor = color
                productListPortraitBackgroundImage = null
            }
            ElementType.PRODUCT_LIST_LANDSCAPE_BACKGROUND -> {
                productListLandscapeBackgroundColor = color
                productListLandscapeBackgroundImage = null
            }
            ElementType.ADD_PRODUCT_PORTRAIT_BACKGROUND -> {
                addProductPortraitBackgroundColor = color
                addProductPortraitBackgroundImage = null
            }
            ElementType.ADD_PRODUCT_LANDSCAPE_BACKGROUND -> {
                addProductLandscapeBackgroundColor = color
                addProductLandscapeBackgroundImage = null
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
    }

    private fun setDefaultProductItemTextColor() {
        val defaultProductItemTextColor = presenter.getDefaultProductItemTextColorValue()

        defaultProductItemTextColor?.let {
            productItemTextColor.setSelectedColor(it)
            productItemTextColorValue = it
        }
    }

    private fun setDefaultDeleteIconColor() {
        val defaultDeleteIconColor = presenter.getDefaultDeleteIconColorValue()

        defaultDeleteIconColor?.let {
            deleteIconColor.setSelectedColor(it)
            deleteIconColorValue = it
        }
    }

    private fun setDefaultColors() {
        setDefaultProductItemBackgroundValue()
        setDefaultProductItemTextColor()
        setDefaultDeleteIconColor()
    }

    private fun setImage(elementType: ElementType, image: ByteArray) {
        when (elementType) {
            ElementType.PRODUCT_LIST_PORTRAIT_BACKGROUND -> {
                productListPortraitBackgroundImage = image
                productListPortraitBackgroundColor = null
            }
            ElementType.PRODUCT_LIST_LANDSCAPE_BACKGROUND -> {
                productListLandscapeBackgroundImage = image
                productListLandscapeBackgroundColor = null
            }
            ElementType.ADD_PRODUCT_PORTRAIT_BACKGROUND -> {
                addProductPortraitBackgroundImage = image
                addProductPortraitBackgroundColor = null
            }
            ElementType.ADD_PRODUCT_LANDSCAPE_BACKGROUND -> {
                addProductLandscapeBackgroundImage = image
                addProductLandscapeBackgroundColor = null
            }
        }
    }

    private fun setBoldProductName(bold: Boolean) {
        if (bold) {
            productItemNameLabel.typeface = Typeface.DEFAULT_BOLD
        } else {
            productItemNameLabel.typeface = Typeface.DEFAULT
        }
    }

    private fun setCopyOptionVisibility(elementType: ElementType?, visible: Boolean) {
        when (elementType) {
            ElementType.PRODUCT_LIST_PORTRAIT_BACKGROUND -> {
                productListLandscapeBackground.showCopyOption(visible)
            }
            ElementType.PRODUCT_LIST_LANDSCAPE_BACKGROUND -> {
                productListPortraitBackground.showCopyOption(visible)
            }
            ElementType.ADD_PRODUCT_PORTRAIT_BACKGROUND -> {
                addProductLandscapeBackground.showCopyOption(visible)
            }
            ElementType.ADD_PRODUCT_LANDSCAPE_BACKGROUND -> {
                addProductPortraitBackground.showCopyOption(visible)
            }
        }
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
                getProductItemTextColorValue(),
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

enum class ElementType(val elementTypeId: Int) {
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