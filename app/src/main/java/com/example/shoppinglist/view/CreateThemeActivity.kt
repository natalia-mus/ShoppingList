package com.example.shoppinglist.view

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import com.example.shoppinglist.model.ElementType
import com.example.shoppinglist.model.Icon
import com.example.shoppinglist.presenter.CreateThemeActivityPresenter
import com.google.android.material.slider.Slider
import kotlinx.android.synthetic.main.product_item.*

class CreateThemeActivity : AppCompatActivity(), CreateThemeActivityContract.CreateThemeActivityView {

    companion object {
        private const val DEFAULT_BACKGROUND_ALFA = 0.3f
        private const val DEFAULT_HINT_ALFA = 0.7f
    }

    private lateinit var presenter: CreateThemeActivityContract.CreateThemeActivityPresenter

    private lateinit var saveButton: Button
    private lateinit var cancelButton: Button
    private lateinit var previousButton: Button
    private lateinit var nextButton: Button
    private lateinit var themeName: EditText
    private lateinit var productListPortraitBackgroundPicker: ImageColorPicker
    private lateinit var productListLandscapeBackgroundPicker: ImageColorPicker
    private lateinit var addProductPortraitBackgroundPicker: ImageColorPicker
    private lateinit var addProductLandscapeBackgroundPicker: ImageColorPicker
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
    private lateinit var productItemBackgroundColorPicker: ImageColorPicker
    private lateinit var productItemTextColorPicker: ImageColorPicker
    private lateinit var deleteIconColorPicker: ImageColorPicker
    private lateinit var addProductText: EditText
    private lateinit var addProductLabel: TextView
    private lateinit var addProductHintText: EditText
    private lateinit var addProductHintLabel: TextView
    private lateinit var addProductHintTransparencySlider: Slider
    private lateinit var addProductTextColorPicker: ImageColorPicker
    private lateinit var addProductLabelColorPicker: ImageColorPicker
    private lateinit var addProductLineColorPicker: ImageColorPicker
    private lateinit var addProductHintColorPicker: ImageColorPicker


    private val backgroundTransparencySliderValueChangedListener = object : Slider.OnChangeListener {
        @SuppressLint("RestrictedApi")
        override fun onValueChange(slider: Slider, value: Float, fromUser: Boolean) {
            productItemBackgroundAlphaPercentageValue = value
            setAlpha(value, ElementType.PRODUCT_ITEM_BACKGROUND_COLOR)
        }
    }

    private val addProductHintTransparencySliderValueChangedListener = object : Slider.OnChangeListener {
        @SuppressLint("RestrictedApi")
        override fun onValueChange(slider: Slider, value: Float, fromUser: Boolean) {
            addProductHintAlphaPercentageValue = value
            setAlpha(value, ElementType.ADD_PRODUCT_HINT_COLOR)
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
    private var productItemBackgroundAlphaPercentageValue = DEFAULT_BACKGROUND_ALFA
    private var productItemBackgroundAlphaValue: String? = ""
    private var productItemBackgroundColorValue: String? = ""
    private var productItemTextColorValue: Int? = null
    private var deleteIconColorValue: Int? = null

    private var addProductTextColorValue: Int? = null
    private var addProductLabelColorValue: Int? = null
    private var addProductLineColorValue: Int? = null
    private var addProductHintAlphaPercentageValue = DEFAULT_HINT_ALFA
    private var addProductHintAlphaValue: String? = ""
    private var addProductHintColorValue: String? = ""

    private var currentCreatorStep = 0
    private var secondStepInitialized = false
    private var thirdStepInitialized = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_theme_first_step)
        presenter = CreateThemeActivityPresenter(this)
        populateCreatorStepsList()
    }

    override fun initView() {
        prepareFirstStep()
    }

    private fun changeView() {
        setContentView(creatorSteps[currentCreatorStep])

        when (creatorSteps[currentCreatorStep]) {
            R.layout.activity_create_theme_first_step -> prepareFirstStep()
            R.layout.activity_create_theme_second_step -> prepareSecondStep()
            R.layout.activity_create_theme_third_step -> prepareThirdStep()
            R.layout.activity_create_theme_last_step -> prepareLastStep()
        }
    }

    private fun copyBackground(elementType: ElementType?) {
        when (elementType) {
            ElementType.PRODUCT_LIST_PORTRAIT_BACKGROUND -> {
                removeBackground(ElementType.PRODUCT_LIST_PORTRAIT_BACKGROUND)
                if (productListLandscapeBackgroundImage != null) {
                    setImage(ElementType.PRODUCT_LIST_LANDSCAPE_BACKGROUND, productListLandscapeBackgroundImage!!)
                    productListPortraitBackgroundPicker.setSelectedImage(productListLandscapeBackgroundImage)

                } else if (productListLandscapeBackgroundColor != null) {
                    setColor(ElementType.PRODUCT_LIST_LANDSCAPE_BACKGROUND, productListLandscapeBackgroundColor)
                    productListPortraitBackgroundPicker.setSelectedColor(productListLandscapeBackgroundColor!!)
                }
            }
            ElementType.PRODUCT_LIST_LANDSCAPE_BACKGROUND -> {
                removeBackground(ElementType.PRODUCT_LIST_LANDSCAPE_BACKGROUND)
                if (productListPortraitBackgroundImage != null) {
                    setImage(ElementType.PRODUCT_LIST_PORTRAIT_BACKGROUND, productListPortraitBackgroundImage!!)
                    productListLandscapeBackgroundPicker.setSelectedImage(productListPortraitBackgroundImage)

                } else if (productListPortraitBackgroundColor != null) {
                    setColor(ElementType.PRODUCT_LIST_PORTRAIT_BACKGROUND, productListPortraitBackgroundColor)
                    productListLandscapeBackgroundPicker.setSelectedColor(productListPortraitBackgroundColor!!)
                }
            }
            ElementType.ADD_PRODUCT_PORTRAIT_BACKGROUND -> {
                removeBackground(ElementType.ADD_PRODUCT_PORTRAIT_BACKGROUND)
                if (addProductLandscapeBackgroundImage != null) {
                    setImage(ElementType.ADD_PRODUCT_LANDSCAPE_BACKGROUND, addProductLandscapeBackgroundImage!!)
                    addProductPortraitBackgroundPicker.setSelectedImage(addProductLandscapeBackgroundImage)

                } else if (addProductLandscapeBackgroundColor != null) {
                    setColor(ElementType.ADD_PRODUCT_LANDSCAPE_BACKGROUND, addProductLandscapeBackgroundColor)
                    addProductPortraitBackgroundPicker.setSelectedColor(addProductLandscapeBackgroundColor!!)
                }
            }
            ElementType.ADD_PRODUCT_LANDSCAPE_BACKGROUND -> {
                removeBackground(ElementType.ADD_PRODUCT_LANDSCAPE_BACKGROUND)
                if (addProductPortraitBackgroundImage != null) {
                    setImage(ElementType.ADD_PRODUCT_PORTRAIT_BACKGROUND, addProductPortraitBackgroundImage!!)
                    addProductLandscapeBackgroundPicker.setSelectedImage(addProductPortraitBackgroundImage)

                } else if (addProductPortraitBackgroundColor != null) {
                    setColor(ElementType.ADD_PRODUCT_PORTRAIT_BACKGROUND, addProductPortraitBackgroundColor)
                    addProductLandscapeBackgroundPicker.setSelectedColor(addProductPortraitBackgroundColor!!)
                }
            }
        }
    }

    private fun getDeleteIconColorValue(): Int {
        if (deleteIconColorValue == null) {
            setDefaultDeleteIconColor()
        }
        return deleteIconColorValue!!
    }

    private fun getProductItemTextColorValue(): Int {
        if (productItemTextColorValue == null) {
            setDefaultProductItemTextColorValue()
        }
        return productItemTextColorValue!!
    }

    private fun getAddProductTextColorValue(): Int {
        if (addProductTextColorValue == null) {
            setDefaultAddProductTextColor()
        }

        return addProductTextColorValue!!
    }

    private fun getAddProductLabelColorValue(): Int {
        if (addProductLabelColorValue == null) {
            setDefaultAddProductLabelColor()
        }

        return addProductLabelColorValue!!
    }

    private fun getAddProductLineColorValue(): Int {
        if (addProductLineColorValue == null) {
            setDefaultAddProductLineColor()
        }

        return addProductLineColorValue!!
    }

    private fun setAddProductTextColor(color: Int) {
        addProductTextColorValue = color
        addProductText.setTextColor(color)
    }

    private fun setAddProductLabelColor(color: Int) {
        addProductLabelColorValue = color
        addProductLabel.setTextColor(color)
        addProductHintLabel.setTextColor(color)
    }

    private fun setAddProductLineColor(color: Int) {
        addProductLineColorValue = color
        val colorStateList = ColorStateList.valueOf(color)
        addProductText.backgroundTintList = colorStateList
        addProductHintText.backgroundTintList = colorStateList
    }

    private fun setDeleteIconColor(color: Int) {
        deleteIconColorValue = color
        productItemButtonDelete.background.setTint(color)
    }

    private fun setProductItemBackground() {
        val colorValue = getProductItemBackgroundValue()
        productItemBackground.background.setTint(Color.parseColor(colorValue))
    }

    private fun setAddProductHintColor() {
        val colorValue = getAddProductHintColorValue()
        addProductHintText.setTextColor(Color.parseColor(colorValue))
    }

    private fun getProductItemBackgroundValue(): String {
        if (productItemBackgroundAlphaValue == null || productItemBackgroundColorValue == null) {
            setDefaultProductItemBackgroundValue()
        }

        return "#$productItemBackgroundAlphaValue$productItemBackgroundColorValue"
    }

    private fun getAddProductHintColorValue(): String {
        if (addProductHintAlphaValue == null || addProductHintColorValue == null) {
            setDefaultAddProductHintColorValue()
        }

        return "#$addProductHintAlphaValue$addProductHintColorValue"
    }

    private fun setDefaultProductItemBackgroundValue() {
        val defaultProductItemBackgroundValue = presenter.getDefaultProductItemBackgroundValue()

        defaultProductItemBackgroundValue?.let {
            val color = Color.parseColor(it)
            val colorWithoutAlpha = Color.rgb(Color.red(color), Color.green(color), Color.blue(color))

            productItemBackgroundColorPicker.setSelectedColor(colorWithoutAlpha)
            setProductItemBackgroundColor(color)
            setProductItemBackgroundAlpha(color.alpha)
        }
    }

    private fun setDefaultAddProductHintColorValue() {
        val defaultAddProductHintColorValue = presenter.getDefaultAddProductHintColorValue()

        defaultAddProductHintColorValue?.let {
            val color = Color.parseColor(it)
            val colorWithoutAlpha = Color.rgb(Color.red(color), Color.green(color), Color.blue(color))

            addProductHintColorPicker.setSelectedColor(colorWithoutAlpha)
            setAddProductHintColor(color)
            setAddProductHintAlpha(color.alpha)
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

    private fun setAddProductHintColor(color: Int) {
        val colorValue = Integer.toHexString(color)
        val alpha = if (colorValue.length == 8) 2 else 0
        addProductHintColorValue = colorValue.drop(alpha)
        setAddProductHintColor()
    }

    private fun setAlpha(alphaPercentage: Float, elementType: ElementType) {
        val alpha = (255 * alphaPercentage).toInt()

        when (elementType) {
            ElementType.PRODUCT_ITEM_BACKGROUND_COLOR -> setProductItemBackgroundAlpha(alpha)
            ElementType.ADD_PRODUCT_HINT_COLOR -> setAddProductHintAlpha(alpha)
        }
    }

    private fun setProductItemBackgroundAlpha(alpha: Int) {
        productItemBackgroundAlphaValue = Integer.toHexString(alpha)

        if (productItemBackgroundAlphaValue?.length == 1) {
            productItemBackgroundAlphaValue = "0$productItemBackgroundAlphaValue"
        }

        setProductItemBackground()
    }

    private fun setAddProductHintAlpha(alpha: Int) {
        addProductHintAlphaValue = Integer.toHexString(alpha)

        if (addProductHintAlphaValue?.length == 1) {
            addProductHintAlphaValue = "0$addProductHintAlphaValue"
        }

        setAddProductHintColor()
    }

    private fun prepareFirstStep() {
        nextButton = findViewById(R.id.create_theme_next)
        productListPortraitBackgroundPicker = findViewById(R.id.create_theme_product_list_portrait_background)
        productListLandscapeBackgroundPicker = findViewById(R.id.create_theme_product_list_landscape_background)
        addProductPortraitBackgroundPicker = findViewById(R.id.create_theme_add_product_portrait_background)
        addProductLandscapeBackgroundPicker = findViewById(R.id.create_theme_add_product_landscape_background)

        nextButton.setOnClickListener {
            nextStep()
        }

        productListPortraitBackgroundPicker.setOnImageColorSetListener(onImageColorSetListener)
        productListLandscapeBackgroundPicker.setOnImageColorSetListener(onImageColorSetListener)
        addProductPortraitBackgroundPicker.setOnImageColorSetListener(onImageColorSetListener)
        addProductLandscapeBackgroundPicker.setOnImageColorSetListener(onImageColorSetListener)


        productListPortraitBackgroundImage?.let { productListPortraitBackgroundPicker.setSelectedImage(it) }
        productListPortraitBackgroundColor?.let { productListPortraitBackgroundPicker.setSelectedColor(it) }

        productListLandscapeBackgroundImage?.let { productListLandscapeBackgroundPicker.setSelectedImage(it) }
        productListLandscapeBackgroundColor?.let { productListLandscapeBackgroundPicker.setSelectedColor(it) }

        addProductPortraitBackgroundImage?.let { addProductPortraitBackgroundPicker.setSelectedImage(it) }
        addProductPortraitBackgroundColor?.let { addProductPortraitBackgroundPicker.setSelectedColor(it) }

        addProductLandscapeBackgroundImage?.let { addProductLandscapeBackgroundPicker.setSelectedImage(it) }
        addProductLandscapeBackgroundColor?.let { addProductLandscapeBackgroundPicker.setSelectedColor(it) }
    }

    private fun prepareSecondStep() {
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
        productItemBackgroundColorPicker = findViewById(R.id.create_theme_background_color)
        productItemTextColorPicker = findViewById(R.id.create_theme_add_product_text_color)
        deleteIconColorPicker = findViewById(R.id.create_theme_delete_icon_color)
        previousButton = findViewById(R.id.create_theme_previous)
        nextButton = findViewById(R.id.create_theme_next)

        boldProductNameSwitch.setOnCheckedChangeListener(boldProductNameOnCheckedChangedListener)

        backgroundTransparencySlider.addOnChangeListener(backgroundTransparencySliderValueChangedListener)

        productItemBackgroundColorPicker.setOnImageColorSetListener(onImageColorSetListener)
        productItemTextColorPicker.setOnImageColorSetListener(onImageColorSetListener)
        deleteIconColorPicker.setOnImageColorSetListener(onImageColorSetListener)

        iconTrashBin.setOnClickListener {
            selectIcon(Icon.TRASH_BIN)
        }

        iconCross.setOnClickListener {
            selectIcon(Icon.CROSS)
        }

        boldProductNameSwitch.setOnClickListener {
            boldProductName = !boldProductName
        }

        previousButton.setOnClickListener {
            previousStep()
        }

        nextButton.setOnClickListener {
            nextStep()
        }

        if (!secondStepInitialized) {
            setSecondStepDefaultColors()
            secondStepInitialized = true

        } else {
            val color = if (productItemBackgroundColorValue != null) Color.parseColor("#$productItemBackgroundColorValue") else null
            color?.let { productItemBackgroundColorPicker.setSelectedColor(it) }

            productItemTextColorValue?.let { productItemTextColorPicker.setSelectedColor(it) }
            deleteIconColorValue?.let { deleteIconColorPicker.setSelectedColor(it) }
            boldProductNameSwitch.isChecked = boldProductName
            selectIcon(deleteIcon)
        }

        backgroundTransparencySlider.value = productItemBackgroundAlphaPercentageValue

        val background = findViewById<ScrollView>(R.id.create_theme_second_step)
        setVisualizationBackground(background)
    }

    private fun prepareThirdStep() {
        addProductText = findViewById(R.id.create_theme_edit_text_visualization_add_product_text)
        addProductLabel = findViewById(R.id.create_theme_edit_text_visualization_add_product_label)
        addProductHintText = findViewById(R.id.create_theme_hint_visualization_add_product_text)
        addProductHintLabel = findViewById(R.id.create_theme_hint_visualization_add_product_label)
        addProductTextColorPicker = findViewById(R.id.create_theme_add_product_text_color)
        addProductLabelColorPicker = findViewById(R.id.create_theme_add_product_label_color)
        addProductLineColorPicker = findViewById(R.id.create_theme_add_product_line_color)
        addProductHintColorPicker = findViewById(R.id.create_theme_add_product_hint_color)
        addProductHintTransparencySlider = findViewById(R.id.create_theme_add_product_hint_transparency)
        previousButton = findViewById(R.id.create_theme_previous)
        nextButton = findViewById(R.id.create_theme_next)

        addProductTextColorPicker.setOnImageColorSetListener(onImageColorSetListener)
        addProductLabelColorPicker.setOnImageColorSetListener(onImageColorSetListener)
        addProductLineColorPicker.setOnImageColorSetListener(onImageColorSetListener)
        addProductHintColorPicker.setOnImageColorSetListener(onImageColorSetListener)

        addProductHintTransparencySlider.addOnChangeListener(addProductHintTransparencySliderValueChangedListener)

        previousButton.setOnClickListener {
            previousStep()
        }

        nextButton.setOnClickListener {
            nextStep()
        }

        val background = findViewById<ScrollView>(R.id.create_theme_third_step)
        setVisualizationBackground(background)

        if (!thirdStepInitialized) {
            setThirdStepDefaultColors()
            thirdStepInitialized = true

        } else {
            addProductTextColorValue?.let { addProductTextColorPicker.setSelectedColor(it) }
            addProductLabelColorValue?.let { addProductLabelColorPicker.setSelectedColor(it) }
            addProductLineColorValue?.let { addProductLineColorPicker.setSelectedColor(it) }

            val color = if (addProductHintColorValue != null && addProductHintColorValue!!.isNotEmpty()) Color.parseColor("#$addProductHintColorValue") else null
            color?.let { addProductHintColorPicker.setSelectedColor(it) }
        }

        addProductHintTransparencySlider.value = addProductHintAlphaPercentageValue
    }

    private fun prepareLastStep() {
        themeName = findViewById(R.id.create_theme_name)
        previousButton = findViewById(R.id.create_theme_previous)
        saveButton = findViewById(R.id.button_save)
        cancelButton = findViewById(R.id.button_cancel)

        previousButton.setOnClickListener {
            previousStep()
        }

        saveButton.setOnClickListener {
            if (validateCurrentStep()) {
                saveTheme()
            }
        }

        cancelButton.setOnClickListener {
            finish()
        }

        themeName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) { }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                name = themeName.text.toString()
            }
        })

        themeName.setText(name)
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
        creatorSteps.add(2, R.layout.activity_create_theme_third_step)
        creatorSteps.add(3, R.layout.activity_create_theme_last_step)
    }

    private fun previousStep() {
        currentCreatorStep--
        changeView()
    }

    private fun removeBackground(backgroundType: ElementType?) {
        when (backgroundType) {
            ElementType.PRODUCT_LIST_PORTRAIT_BACKGROUND -> {
                productListPortraitBackgroundColor = null
                productListPortraitBackgroundImage = null
                productListPortraitBackgroundPicker.removeBackground()
                productListLandscapeBackgroundPicker.showCopyOption(false)
            }
            ElementType.PRODUCT_LIST_LANDSCAPE_BACKGROUND -> {
                productListLandscapeBackgroundColor = null
                productListLandscapeBackgroundImage = null
                productListLandscapeBackgroundPicker.removeBackground()
                productListPortraitBackgroundPicker.showCopyOption(false)
            }
            ElementType.ADD_PRODUCT_PORTRAIT_BACKGROUND -> {
                addProductPortraitBackgroundColor = null
                addProductPortraitBackgroundImage = null
                addProductPortraitBackgroundPicker.removeBackground()
                addProductLandscapeBackgroundPicker.showCopyOption(false)
            }
            ElementType.ADD_PRODUCT_LANDSCAPE_BACKGROUND -> {
                addProductLandscapeBackgroundColor = null
                addProductLandscapeBackgroundImage = null
                addProductLandscapeBackgroundPicker.removeBackground()
                addProductPortraitBackgroundPicker.showCopyOption(false)
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
            boldProductName,
            getAddProductTextColorValue(),
            getAddProductLabelColorValue(),
            getAddProductLineColorValue(),
            getAddProductHintColorValue()
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
            ElementType.ADD_PRODUCT_TEXT_COLOR -> {
                if (color != null) {
                    setAddProductTextColor(color)
                }
            }
            ElementType.ADD_PRODUCT_LABEL_COLOR -> {
                if (color != null) {
                    setAddProductLabelColor(color)
                }
            }
            ElementType.ADD_PRODUCT_LINE_COLOR -> {
                if (color != null) {
                    setAddProductLineColor(color)
                }
            }
            ElementType.ADD_PRODUCT_HINT_COLOR -> {
                if (color != null) {
                    setAddProductHintColor(color)
                }
            }
        }
    }

    private fun setDefaultProductItemTextColorValue() {
        val defaultProductItemTextColor = presenter.getDefaultProductItemTextColorValue()

        defaultProductItemTextColor?.let {
            productItemTextColorPicker.setSelectedColor(it)
            productItemTextColorValue = it
        }
    }

    private fun setDefaultAddProductTextColor() {
        val defaultAddProductTextColorValue = presenter.getDefaultAddProductTextColorValue()

        defaultAddProductTextColorValue?.let {
            addProductTextColorPicker.setSelectedColor(it)
            addProductTextColorValue = it
        }
    }

    private fun setDefaultAddProductLabelColor() {
        val defaultAddProductLabelColorValue = presenter.getDefaultAddProductLabelColorValue()

        defaultAddProductLabelColorValue?.let {
            addProductLabelColorPicker.setSelectedColor(it)
            addProductLabelColorValue = it
        }
    }

    private fun setDefaultAddProductLineColor() {
        val defaultAddProductLineColorValue = presenter.getDefaultAddProductLineColorValue()

        defaultAddProductLineColorValue?.let {
            addProductLineColorPicker.setSelectedColor(it)
            addProductLineColorValue = it
        }
    }

    private fun setDefaultDeleteIconColor() {
        val defaultDeleteIconColorValue = presenter.getDefaultDeleteIconColorValue()

        defaultDeleteIconColorValue?.let {
            deleteIconColorPicker.setSelectedColor(it)
            deleteIconColorValue = it
        }
    }

    private fun setSecondStepDefaultColors() {
        setDefaultProductItemBackgroundValue()
        setDefaultProductItemTextColorValue()
        setDefaultDeleteIconColor()
    }

    private fun setThirdStepDefaultColors() {
        setDefaultAddProductTextColor()
        setDefaultAddProductLabelColor()
        setDefaultAddProductHintColorValue()
        setDefaultAddProductLineColor()
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
                productListLandscapeBackgroundPicker.showCopyOption(visible)
            }
            ElementType.PRODUCT_LIST_LANDSCAPE_BACKGROUND -> {
                productListPortraitBackgroundPicker.showCopyOption(visible)
            }
            ElementType.ADD_PRODUCT_PORTRAIT_BACKGROUND -> {
                addProductLandscapeBackgroundPicker.showCopyOption(visible)
            }
            ElementType.ADD_PRODUCT_LANDSCAPE_BACKGROUND -> {
                addProductPortraitBackgroundPicker.showCopyOption(visible)
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
            R.layout.activity_create_theme_second_step -> presenter.validateSecondStep()
            R.layout.activity_create_theme_third_step -> presenter.validateThirdStep(
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
                boldProductName,
                getAddProductTextColorValue(),
                getAddProductLabelColorValue(),
                getAddProductHintColorValue(),
                getAddProductLineColorValue()
            )
            R.layout.activity_create_theme_last_step -> {
                presenter.validateLastStep(name)
            }
            else -> true
        }

        return when (validationResult) {
            ValidationResult.EMPTY_NAME -> {
                Toast.makeText(this, resources.getString(R.string.empty_theme_name), Toast.LENGTH_LONG).show()
                false
            }
            ValidationResult.NOTHING_TO_KEEP -> {
                Toast.makeText(this, resources.getString(R.string.nothing_to_keep), Toast.LENGTH_LONG).show()
                false
            }
            else -> true
        }
    }
}