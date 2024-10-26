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
import com.example.shoppinglist.ImageUtils
import com.example.shoppinglist.R
import com.example.shoppinglist.ValidationResult
import com.example.shoppinglist.contract.CreateThemeActivityContract
import com.example.shoppinglist.control.ImageColorPicker
import com.example.shoppinglist.model.Icon
import com.example.shoppinglist.model.StyleableElementType
import com.example.shoppinglist.presenter.CreateThemeActivityPresenter
import com.google.android.material.slider.Slider
import kotlinx.android.synthetic.main.activity_create_theme_first_step.*
import kotlinx.android.synthetic.main.activity_create_theme_last_step.*
import kotlinx.android.synthetic.main.activity_create_theme_second_step.*
import kotlinx.android.synthetic.main.activity_create_theme_third_step.*
import kotlinx.android.synthetic.main.product_item.*

class CreateThemeActivity : ToolbarProvidingActivity(false), CreateThemeActivityContract.CreateThemeActivityView {

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
            productItemBackgroundColor.setAlpha(value)
            setProductItemBackground()
        }
    }

    private val addProductHintTransparencySliderValueChangedListener = object : Slider.OnChangeListener {
        @SuppressLint("RestrictedApi")
        override fun onValueChange(slider: Slider, value: Float, fromUser: Boolean) {
            addProductHintColor.setAlpha(value)
            setAddProductHintColor()
        }
    }

    private val boldProductNameOnCheckedChangedListener = object : CompoundButton.OnCheckedChangeListener {
        override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
            setBoldProductName(isChecked)
        }
    }

    private val onImageColorSetListener = object : ImageColorPicker.OnImageColorSetListener {
        override fun onCopyImage(styleableElementType: StyleableElementType?) {
            copyBackground(styleableElementType)
        }

        override fun onColorSet(styleableElementType: StyleableElementType?, color: Int) {
            setColor(styleableElementType, color)
            setCopyOptionVisibility(styleableElementType, true)
        }

        override fun onImageSet(styleableElementType: StyleableElementType?, image: ByteArray) {
            if (styleableElementType != null) {
                setImage(styleableElementType, image)
                setCopyOptionVisibility(styleableElementType, true)
            }
        }

        override fun onRemoveImage(styleableElementType: StyleableElementType?) {
            removeBackground(styleableElementType)
        }
    }

    private val creatorSteps = ArrayList<Int>()

    private var productListPortraitBackgroundImage: ByteArray? = null
    private var productListLandscapeBackgroundImage: ByteArray? = null
    private var addProductPortraitBackgroundImage: ByteArray? = null
    private var addProductLandscapeBackgroundImage: ByteArray? = null

    private var productListPortraitBackgroundColor = com.example.shoppinglist.Color(null)
    private var productListLandscapeBackgroundColor = com.example.shoppinglist.Color(null)
    private var addProductPortraitBackgroundColor = com.example.shoppinglist.Color(null)
    private var addProductLandscapeBackgroundColor = com.example.shoppinglist.Color(null)

    private var name = ""
    private var deleteIcon = Icon.TRASH_BIN
    private var boldProductName = true
    private var productItemBackgroundColor = com.example.shoppinglist.Color(null, DEFAULT_BACKGROUND_ALFA)
    private var productItemTextColor = com.example.shoppinglist.Color(null)
    private var deleteIconColor = com.example.shoppinglist.Color(null)

    private var addProductTextColor = com.example.shoppinglist.Color(null)
    private var addProductLabelColor = com.example.shoppinglist.Color(null)
    private var addProductLineColor = com.example.shoppinglist.Color(null)
    private var addProductHintColor = com.example.shoppinglist.Color(null, DEFAULT_HINT_ALFA)

    private var currentCreatorStep = 0
    private var secondStepInitialized = false
    private var thirdStepInitialized = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_theme_first_step)
        setToolbar(create_theme_first_step)

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

    private fun copyBackground(styleableElementType: StyleableElementType?) {
        when (styleableElementType) {
            StyleableElementType.PRODUCT_LIST_PORTRAIT_BACKGROUND -> {
                removeBackground(StyleableElementType.PRODUCT_LIST_PORTRAIT_BACKGROUND)
                if (productListLandscapeBackgroundImage != null) {
                    setImage(StyleableElementType.PRODUCT_LIST_LANDSCAPE_BACKGROUND, productListLandscapeBackgroundImage!!)
                    productListPortraitBackgroundPicker.setSelectedImage(productListLandscapeBackgroundImage)

                } else if (productListLandscapeBackgroundColor.getColorInt() != null) {
                    setColor(StyleableElementType.PRODUCT_LIST_LANDSCAPE_BACKGROUND, productListLandscapeBackgroundColor.getColorInt())
                    productListPortraitBackgroundPicker.setSelectedColor(productListLandscapeBackgroundColor.getColorInt()!!)
                }
            }
            StyleableElementType.PRODUCT_LIST_LANDSCAPE_BACKGROUND -> {
                removeBackground(StyleableElementType.PRODUCT_LIST_LANDSCAPE_BACKGROUND)
                if (productListPortraitBackgroundImage != null) {
                    setImage(StyleableElementType.PRODUCT_LIST_PORTRAIT_BACKGROUND, productListPortraitBackgroundImage!!)
                    productListLandscapeBackgroundPicker.setSelectedImage(productListPortraitBackgroundImage)

                } else if (productListPortraitBackgroundColor.getColorInt() != null) {
                    setColor(StyleableElementType.PRODUCT_LIST_PORTRAIT_BACKGROUND, productListPortraitBackgroundColor.getColorInt())
                    productListLandscapeBackgroundPicker.setSelectedColor(productListPortraitBackgroundColor.getColorInt()!!)
                }
            }
            StyleableElementType.ADD_PRODUCT_PORTRAIT_BACKGROUND -> {
                removeBackground(StyleableElementType.ADD_PRODUCT_PORTRAIT_BACKGROUND)
                if (addProductLandscapeBackgroundImage != null) {
                    setImage(StyleableElementType.ADD_PRODUCT_LANDSCAPE_BACKGROUND, addProductLandscapeBackgroundImage!!)
                    addProductPortraitBackgroundPicker.setSelectedImage(addProductLandscapeBackgroundImage)

                } else if (addProductLandscapeBackgroundColor.getColorInt() != null) {
                    setColor(StyleableElementType.ADD_PRODUCT_LANDSCAPE_BACKGROUND, addProductLandscapeBackgroundColor.getColorInt())
                    addProductPortraitBackgroundPicker.setSelectedColor(addProductLandscapeBackgroundColor.getColorInt()!!)
                }
            }
            StyleableElementType.ADD_PRODUCT_LANDSCAPE_BACKGROUND -> {
                removeBackground(StyleableElementType.ADD_PRODUCT_LANDSCAPE_BACKGROUND)
                if (addProductPortraitBackgroundImage != null) {
                    setImage(StyleableElementType.ADD_PRODUCT_PORTRAIT_BACKGROUND, addProductPortraitBackgroundImage!!)
                    addProductLandscapeBackgroundPicker.setSelectedImage(addProductPortraitBackgroundImage)

                } else if (addProductPortraitBackgroundColor.getColorInt() != null) {
                    setColor(StyleableElementType.ADD_PRODUCT_PORTRAIT_BACKGROUND, addProductPortraitBackgroundColor.getColorInt())
                    addProductLandscapeBackgroundPicker.setSelectedColor(addProductPortraitBackgroundColor.getColorInt()!!)
                }
            }
        }
    }

    private fun getDeleteIconColorValue(): Int {
        if (deleteIconColor.getColorInt() == null) {
            setDefaultDeleteIconColor()
        }
        return deleteIconColor.getColorInt()!!
    }

    private fun getProductItemTextColorValue(): Int {
        if (productItemTextColor.getColorInt() == null) {
            setDefaultProductItemTextColorValue()
        }
        return productItemTextColor.getColorInt()!!
    }

    private fun getAddProductTextColorValue(): Int {
        if (addProductTextColor.getColorInt() == null) {
            setDefaultAddProductTextColor()
        }

        return addProductTextColor.getColorInt()!!
    }

    private fun getAddProductLabelColorValue(): Int {
        if (addProductLabelColor.getColorInt() == null) {
            setDefaultAddProductLabelColor()
        }

        return addProductLabelColor.getColorInt()!!
    }

    private fun getAddProductLineColorValue(): Int {
        if (addProductLineColor.getColorInt() == null) {
            setDefaultAddProductLineColor()
        }

        return addProductLineColor.getColorInt()!!
    }

    private fun setAddProductTextColor(color: Int) {
        addProductTextColor.setColor(color)
        addProductText.setTextColor(color)
    }

    private fun setAddProductLabelColor(color: Int) {
        addProductLabelColor.setColor(color)
        addProductLabel.setTextColor(color)
        addProductHintLabel.setTextColor(color)
    }

    private fun setAddProductLineColor(color: Int) {
        addProductLineColor.setColor(color)
        val colorStateList = ColorStateList.valueOf(color)
        addProductText.backgroundTintList = colorStateList
        addProductHintText.backgroundTintList = colorStateList
    }

    private fun setDeleteIconColor(color: Int) {
        deleteIconColor.setColor(color)
        productItemButtonDelete.background.setTint(color)
    }

    private fun setProductItemBackground() {
        productItemBackground.background.setTint(Color.parseColor(productItemBackgroundColor.getValue()))
    }

    private fun setAddProductHintColor() {
        addProductHintText.setTextColor(Color.parseColor(addProductHintColor.getValue()))
    }

    private fun setDefaultProductItemBackgroundValue() {
        val defaultProductItemBackgroundValue = presenter.getDefaultProductItemBackgroundValue()

        defaultProductItemBackgroundValue?.let {
            val color = Color.parseColor(it)
            val colorWithoutAlpha = Color.rgb(Color.red(color), Color.green(color), Color.blue(color))

            productItemBackgroundColorPicker.setSelectedColor(colorWithoutAlpha)
        }
    }

    private fun setDefaultAddProductHintColorValue() {
        val defaultAddProductHintColorValue = presenter.getDefaultAddProductHintColorValue()

        defaultAddProductHintColorValue?.let {
            val color = Color.parseColor(it)
            val colorWithoutAlpha = Color.rgb(Color.red(color), Color.green(color), Color.blue(color))

            addProductHintColorPicker.setSelectedColor(colorWithoutAlpha)
        }
    }

    private fun setProductItemTextColor(color: Int) {
        productItemTextColor.setColor(color)
        productItemNameLabel.setTextColor(color)
        productItemQuantityLabel.setTextColor(color)
        productItemPriorityLabel.setTextColor(color)
        productItemQuantityValue.setTextColor(color)
        productItemPriorityValue.setTextColor(color)
    }

    private fun prepareFirstStep() {
        setToolbar(create_theme_first_step)

        productListPortraitBackgroundPicker = findViewById(R.id.create_theme_product_list_portrait_background)
        productListLandscapeBackgroundPicker = findViewById(R.id.create_theme_product_list_landscape_background)
        addProductPortraitBackgroundPicker = findViewById(R.id.create_theme_add_product_portrait_background)
        addProductLandscapeBackgroundPicker = findViewById(R.id.create_theme_add_product_landscape_background)

        prepareStepButtons(false, true)

        productListPortraitBackgroundPicker.setOnImageColorSetListener(onImageColorSetListener)
        productListLandscapeBackgroundPicker.setOnImageColorSetListener(onImageColorSetListener)
        addProductPortraitBackgroundPicker.setOnImageColorSetListener(onImageColorSetListener)
        addProductLandscapeBackgroundPicker.setOnImageColorSetListener(onImageColorSetListener)


        productListPortraitBackgroundImage?.let { productListPortraitBackgroundPicker.setSelectedImage(it) }
        productListPortraitBackgroundColor.getColorInt()?.let { productListPortraitBackgroundPicker.setSelectedColor(it) }

        productListLandscapeBackgroundImage?.let { productListLandscapeBackgroundPicker.setSelectedImage(it) }
        productListLandscapeBackgroundColor.getColorInt()?.let { productListLandscapeBackgroundPicker.setSelectedColor(it) }

        addProductPortraitBackgroundImage?.let { addProductPortraitBackgroundPicker.setSelectedImage(it) }
        addProductPortraitBackgroundColor.getColorInt()?.let { addProductPortraitBackgroundPicker.setSelectedColor(it) }

        addProductLandscapeBackgroundImage?.let { addProductLandscapeBackgroundPicker.setSelectedImage(it) }
        addProductLandscapeBackgroundColor.getColorInt()?.let { addProductLandscapeBackgroundPicker.setSelectedColor(it) }
    }

    private fun prepareSecondStep() {
        setToolbar(create_theme_second_step)

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

        prepareStepButtons(true, true)


        if (!secondStepInitialized) {
            setSecondStepDefaultColors()
            secondStepInitialized = true

        } else {
            productItemBackgroundColor.getColorInt()?.let { productItemBackgroundColorPicker.setSelectedColor(it) }

            productItemTextColor.getColorInt()?.let { productItemTextColorPicker.setSelectedColor(it) }
            deleteIconColor.getColorInt()?.let { deleteIconColorPicker.setSelectedColor(it) }
            boldProductNameSwitch.isChecked = boldProductName
            selectIcon(deleteIcon)
        }

        backgroundTransparencySlider.value = productItemBackgroundColor.getAlphaPercentage()

        val background = findViewById<ScrollView>(R.id.create_theme_second_step_content)
        setVisualizationBackground(background)
    }

    private fun prepareThirdStep() {
        setToolbar(create_theme_third_step)

        addProductText = findViewById(R.id.create_theme_edit_text_visualization_add_product_text)
        addProductLabel = findViewById(R.id.create_theme_edit_text_visualization_add_product_label)
        addProductHintText = findViewById(R.id.create_theme_hint_visualization_add_product_text)
        addProductHintLabel = findViewById(R.id.create_theme_hint_visualization_add_product_label)
        addProductTextColorPicker = findViewById(R.id.create_theme_add_product_text_color)
        addProductLabelColorPicker = findViewById(R.id.create_theme_add_product_label_color)
        addProductLineColorPicker = findViewById(R.id.create_theme_add_product_line_color)
        addProductHintColorPicker = findViewById(R.id.create_theme_add_product_hint_color)
        addProductHintTransparencySlider = findViewById(R.id.create_theme_add_product_hint_transparency)

        addProductTextColorPicker.setOnImageColorSetListener(onImageColorSetListener)
        addProductLabelColorPicker.setOnImageColorSetListener(onImageColorSetListener)
        addProductLineColorPicker.setOnImageColorSetListener(onImageColorSetListener)
        addProductHintColorPicker.setOnImageColorSetListener(onImageColorSetListener)

        addProductHintTransparencySlider.addOnChangeListener(addProductHintTransparencySliderValueChangedListener)

        prepareStepButtons(true, true)

        val background = findViewById<ScrollView>(R.id.create_theme_third_step_content)
        setVisualizationBackground(background)

        if (!thirdStepInitialized) {
            setThirdStepDefaultColors()
            thirdStepInitialized = true

        } else {
            addProductTextColor.getColorInt()?.let { addProductTextColorPicker.setSelectedColor(it) }
            addProductLabelColor.getColorInt()?.let { addProductLabelColorPicker.setSelectedColor(it) }
            addProductLineColor.getColorInt()?.let { addProductLineColorPicker.setSelectedColor(it) }
            addProductHintColor.getColorInt()?.let { addProductHintColorPicker.setSelectedColor(it) }
        }

        addProductHintTransparencySlider.value = addProductHintColor.getAlphaPercentage()
    }

    private fun prepareLastStep() {
        setToolbar(create_theme_last_step)

        themeName = findViewById(R.id.create_theme_name)
        saveButton = findViewById(R.id.button_save)
        cancelButton = findViewById(R.id.button_cancel)

        prepareStepButtons(true, false)

        saveButton.setOnClickListener {
            if (validateCurrentStep()) {
                saveTheme()
            }
        }

        cancelButton.setOnClickListener {
            finish()
        }

        themeName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                name = themeName.text.toString()
            }
        })

        themeName.setText(name)
    }

    private fun prepareStepButtons(preparePreviousButton: Boolean, prepareNextButton: Boolean) {
        previousButton = findViewById(R.id.create_theme_previous)
        if (preparePreviousButton) {
            previousButton.setOnClickListener {
                previousStep()
            }
        } else {
            previousButton.visibility = View.GONE
        }

        nextButton = findViewById(R.id.create_theme_next)
        if (prepareNextButton) {
            nextButton.setOnClickListener {
                nextStep()
            }
        } else {
            nextButton.visibility = View.GONE
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
        creatorSteps.add(2, R.layout.activity_create_theme_third_step)
        creatorSteps.add(3, R.layout.activity_create_theme_last_step)
    }

    private fun previousStep() {
        currentCreatorStep--
        changeView()
    }

    private fun removeBackground(backgroundType: StyleableElementType?) {
        when (backgroundType) {
            StyleableElementType.PRODUCT_LIST_PORTRAIT_BACKGROUND -> {
                productListPortraitBackgroundColor.removeColor()
                productListPortraitBackgroundImage = null
                productListPortraitBackgroundPicker.removeBackground()
                productListLandscapeBackgroundPicker.showCopyOption(false)
            }
            StyleableElementType.PRODUCT_LIST_LANDSCAPE_BACKGROUND -> {
                productListLandscapeBackgroundColor.removeColor()
                productListLandscapeBackgroundImage = null
                productListLandscapeBackgroundPicker.removeBackground()
                productListPortraitBackgroundPicker.showCopyOption(false)
            }
            StyleableElementType.ADD_PRODUCT_PORTRAIT_BACKGROUND -> {
                addProductPortraitBackgroundColor.removeColor()
                addProductPortraitBackgroundImage = null
                addProductPortraitBackgroundPicker.removeBackground()
                addProductLandscapeBackgroundPicker.showCopyOption(false)
            }
            StyleableElementType.ADD_PRODUCT_LANDSCAPE_BACKGROUND -> {
                addProductLandscapeBackgroundColor.removeColor()
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
            productListPortraitBackgroundColor.getColorInt(),
            productListLandscapeBackgroundColor.getColorInt(),
            addProductPortraitBackgroundColor.getColorInt(),
            addProductLandscapeBackgroundColor.getColorInt(),
            productItemBackgroundColor.getValue(),
            getProductItemTextColorValue(),
            getDeleteIconColorValue(),
            deleteIcon,
            boldProductName,
            getAddProductTextColorValue(),
            getAddProductLabelColorValue(),
            getAddProductLineColorValue(),
            addProductHintColor.getValue()
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
        setColor(StyleableElementType.DELETE_ICON_COLOR, deleteIconColor.getColorInt())
    }

    private fun setColor(styleableElementType: StyleableElementType?, color: Int?) {
        when (styleableElementType) {
            StyleableElementType.PRODUCT_LIST_PORTRAIT_BACKGROUND -> {
                color?.let { productListPortraitBackgroundColor.setColor(it) }
                productListPortraitBackgroundImage = null
            }
            StyleableElementType.PRODUCT_LIST_LANDSCAPE_BACKGROUND -> {
                color?.let { productListLandscapeBackgroundColor.setColor(it) }
                productListLandscapeBackgroundImage = null
            }
            StyleableElementType.ADD_PRODUCT_PORTRAIT_BACKGROUND -> {
                color?.let { addProductPortraitBackgroundColor.setColor(it) }
                addProductPortraitBackgroundImage = null
            }
            StyleableElementType.ADD_PRODUCT_LANDSCAPE_BACKGROUND -> {
                color?.let { addProductLandscapeBackgroundColor.setColor(it) }
                addProductLandscapeBackgroundImage = null
            }
            StyleableElementType.PRODUCT_ITEM_BACKGROUND_COLOR -> {
                if (color != null) {
                    productItemBackgroundColor.setColor(color)
                    setProductItemBackground()
                }
            }
            StyleableElementType.PRODUCT_ITEM_TEXT_COLOR -> {
                color?.let { setProductItemTextColor(it) }
            }
            StyleableElementType.DELETE_ICON_COLOR -> {
                color?.let { setDeleteIconColor(it) }
            }
            StyleableElementType.ADD_PRODUCT_TEXT_COLOR -> {
                color?.let { setAddProductTextColor(it) }
            }
            StyleableElementType.ADD_PRODUCT_LABEL_COLOR -> {
                color?.let { setAddProductLabelColor(it) }
            }
            StyleableElementType.ADD_PRODUCT_LINE_COLOR -> {
                color?.let { setAddProductLineColor(it) }
            }
            StyleableElementType.ADD_PRODUCT_HINT_COLOR -> {
                if (color != null) {
                    addProductHintColor.setColor(color)
                    setAddProductHintColor()
                }
            }
        }
    }

    private fun setDefaultProductItemTextColorValue() {
        val defaultProductItemTextColor = presenter.getDefaultProductItemTextColorValue()

        defaultProductItemTextColor?.let {
            productItemTextColorPicker.setSelectedColor(it)
            productItemTextColor.setColor(it)
        }
    }

    private fun setDefaultAddProductTextColor() {
        val defaultAddProductTextColorValue = presenter.getDefaultAddProductTextColorValue()

        defaultAddProductTextColorValue?.let {
            addProductTextColorPicker.setSelectedColor(it)
            addProductTextColor.setColor(it)
        }
    }

    private fun setDefaultAddProductLabelColor() {
        val defaultAddProductLabelColorValue = presenter.getDefaultAddProductLabelColorValue()

        defaultAddProductLabelColorValue?.let {
            addProductLabelColorPicker.setSelectedColor(it)
            addProductLabelColor.setColor(it)
        }
    }

    private fun setDefaultAddProductLineColor() {
        val defaultAddProductLineColorValue = presenter.getDefaultAddProductLineColorValue()

        defaultAddProductLineColorValue?.let {
            addProductLineColorPicker.setSelectedColor(it)
            addProductLineColor.setColor(it)
        }
    }

    private fun setDefaultDeleteIconColor() {
        val defaultDeleteIconColorValue = presenter.getDefaultDeleteIconColorValue()

        defaultDeleteIconColorValue?.let {
            deleteIconColorPicker.setSelectedColor(it)
            deleteIconColor.setColor(it)
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

    private fun setImage(styleableElementType: StyleableElementType, image: ByteArray) {
        when (styleableElementType) {
            StyleableElementType.PRODUCT_LIST_PORTRAIT_BACKGROUND -> {
                productListPortraitBackgroundImage = image
                productListPortraitBackgroundColor.removeColor()
            }
            StyleableElementType.PRODUCT_LIST_LANDSCAPE_BACKGROUND -> {
                productListLandscapeBackgroundImage = image
                productListLandscapeBackgroundColor.removeColor()
            }
            StyleableElementType.ADD_PRODUCT_PORTRAIT_BACKGROUND -> {
                addProductPortraitBackgroundImage = image
                addProductPortraitBackgroundColor.removeColor()
            }
            StyleableElementType.ADD_PRODUCT_LANDSCAPE_BACKGROUND -> {
                addProductLandscapeBackgroundImage = image
                addProductLandscapeBackgroundColor.removeColor()
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

    private fun setCopyOptionVisibility(styleableElementType: StyleableElementType?, visible: Boolean) {
        when (styleableElementType) {
            StyleableElementType.PRODUCT_LIST_PORTRAIT_BACKGROUND -> {
                productListLandscapeBackgroundPicker.showCopyOption(visible)
            }
            StyleableElementType.PRODUCT_LIST_LANDSCAPE_BACKGROUND -> {
                productListPortraitBackgroundPicker.showCopyOption(visible)
            }
            StyleableElementType.ADD_PRODUCT_PORTRAIT_BACKGROUND -> {
                addProductLandscapeBackgroundPicker.showCopyOption(visible)
            }
            StyleableElementType.ADD_PRODUCT_LANDSCAPE_BACKGROUND -> {
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

            } else if (addProductPortraitBackgroundColor.getColorInt() != null) {
                backgroundColor = addProductPortraitBackgroundColor.getColorInt()

            } else {
                backgroundImage = ImageUtils.getImageAsByteArray(ResourcesCompat.getDrawable(resources, R.drawable.theme_grocery_list_portrait, null))
            }

        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            if (addProductLandscapeBackgroundImage != null) {
                backgroundImage = addProductLandscapeBackgroundImage

            } else if (addProductLandscapeBackgroundColor.getColorInt() != null) {
                backgroundColor = addProductLandscapeBackgroundColor.getColorInt()

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
                productListPortraitBackgroundColor.getColorInt(),
                productListLandscapeBackgroundColor.getColorInt(),
                addProductPortraitBackgroundColor.getColorInt(),
                addProductLandscapeBackgroundColor.getColorInt(),
                productItemBackgroundColor.getValue(),
                getProductItemTextColorValue(),
                getDeleteIconColorValue(),
                deleteIcon,
                boldProductName,
                getAddProductTextColorValue(),
                getAddProductLabelColorValue(),
                addProductHintColor.getValue(),
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