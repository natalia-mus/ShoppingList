package com.example.shoppinglist.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Color
import android.util.Log
import androidx.core.content.contentValuesOf
import androidx.core.content.res.ResourcesCompat
import androidx.core.database.getBlobOrNull
import androidx.core.database.getIntOrNull
import androidx.core.database.getStringOrNull
import com.example.shoppinglist.ImageUtils
import com.example.shoppinglist.R
import com.example.shoppinglist.ThemeConstants
import com.example.shoppinglist.model.Icon
import com.example.shoppinglist.model.Product
import com.example.shoppinglist.model.Theme

object TableInfo {
    const val DATABASE_NAME = "shopping_list.db"
    const val DATABASE_VERSION = 8
    const val TABLE_NAME_PRODUCTS = "shopping_list"
    const val TABLE_NAME_THEMES = "themes"
    const val TABLE_NAME_COLOR_SETS = "color_sets"
    const val COLUMN_ID = "id"
    const val COLUMN_NAME = "name"
    const val COLUMN_BUILT_IN_THEME = "builtInTheme"
    const val COLUMN_QUANTITY = "quantity"
    const val COLUMN_PRIORITY = "priority"
    const val COLUMN_LIST_BACKGROUND_IMAGE_PORTRAIT = "listBackgroundImagePortrait"
    const val COLUMN_LIST_BACKGROUND_IMAGE_LANDSCAPE = "listBackgroundImageLandscape"
    const val COLUMN_ADD_PRODUCT_BACKGROUND_IMAGE_PORTRAIT = "addProductBackgroundImagePortrait"
    const val COLUMN_ADD_PRODUCT_BACKGROUND_IMAGE_LANDSCAPE = "addProductBackgroundImageLandscape"
    const val COLUMN_LIST_BACKGROUND_COLOR_PORTRAIT = "listBackgroundColorPortrait"
    const val COLUMN_LIST_BACKGROUND_COLOR_LANDSCAPE = "listBackgroundColorLandscape"
    const val COLUMN_ADD_PRODUCT_BACKGROUND_COLOR_PORTRAIT = "addProductBackgroundColorPortrait"
    const val COLUMN_ADD_PRODUCT_BACKGROUND_COLOR_LANDSCAPE = "addProductBackgroundColorLandscape"
    const val COLUMN_PRODUCT_ITEM_BACKGROUND_VALUE = "productItemBackgroundValue"
    const val COLUMN_PRODUCT_ITEM_TEXT_COLOR_VALUE = "productItemTextColorValue"
    const val COLUMN_DELETE_ICON_COLOR_VALUE = "deleteIconColorValue"
    const val COLUMN_DELETE_ICON = "deleteIcon"
    const val COLUMN_BOLD_PRODUCT_NAME = "boldProductName"
    const val COLUMN_ADD_PRODUCT_TEXT_COLOR_VALUE = "addProductTextColorValue"
    const val COLUMN_ADD_PRODUCT_LABEL_COLOR_VALUE = "addProductLabelColorValue"
    const val COLUMN_ADD_PRODUCT_LINE_COLOR_VALUE = "addProductLineColorValue"
    const val COLUMN_ADD_PRODUCT_HINT_COLOR_VALUE = "addProductHintColorValue"
    const val COLUMN_COLOR_SET_ID = "colorSetId"
    const val COLUMN_PRIMARY_COLOR_VALUE = "primaryColorValue"
    const val COLUMN_SECONDARY_COLOR_VALUE = "secondaryColorValue"
}

object BasicSQLCommands {

    const val CREATE_TABLE_PRODUCTS = "CREATE TABLE ${TableInfo.TABLE_NAME_PRODUCTS} " +
            "(${TableInfo.COLUMN_ID} INTEGER PRIMARY KEY, " +
            "${TableInfo.COLUMN_NAME} TEXT NOT NULL, " +
            "${TableInfo.COLUMN_QUANTITY} TEXT, " +
            "${TableInfo.COLUMN_PRIORITY} INT NOT NULL)"

    const val CREATE_TABLE_THEMES = "CREATE TABLE ${TableInfo.TABLE_NAME_THEMES} " +
            "(" +
            "${TableInfo.COLUMN_ID} INTEGER PRIMARY KEY, " +
            "${TableInfo.COLUMN_NAME} TEXT NOT NULL, " +
            "${TableInfo.COLUMN_BUILT_IN_THEME} INTEGER NOT NULL, " +
            "${TableInfo.COLUMN_LIST_BACKGROUND_IMAGE_PORTRAIT} BLOB, " +
            "${TableInfo.COLUMN_LIST_BACKGROUND_IMAGE_LANDSCAPE} BLOB, " +
            "${TableInfo.COLUMN_ADD_PRODUCT_BACKGROUND_IMAGE_PORTRAIT} BLOB, " +
            "${TableInfo.COLUMN_ADD_PRODUCT_BACKGROUND_IMAGE_LANDSCAPE} BLOB, " +
            "${TableInfo.COLUMN_LIST_BACKGROUND_COLOR_PORTRAIT} INTEGER, " +
            "${TableInfo.COLUMN_LIST_BACKGROUND_COLOR_LANDSCAPE} INTEGER, " +
            "${TableInfo.COLUMN_ADD_PRODUCT_BACKGROUND_COLOR_PORTRAIT} INTEGER, " +
            "${TableInfo.COLUMN_ADD_PRODUCT_BACKGROUND_COLOR_LANDSCAPE} INTEGER, " +
            "${TableInfo.COLUMN_PRODUCT_ITEM_BACKGROUND_VALUE} TEXT, " +
            "${TableInfo.COLUMN_PRODUCT_ITEM_TEXT_COLOR_VALUE} INTEGER, " +
            "${TableInfo.COLUMN_DELETE_ICON_COLOR_VALUE} INTEGER, " +
            "${TableInfo.COLUMN_DELETE_ICON} INTEGER, " +
            "${TableInfo.COLUMN_BOLD_PRODUCT_NAME} INTEGER, " +
            "${TableInfo.COLUMN_ADD_PRODUCT_TEXT_COLOR_VALUE} INTEGER, " +
            "${TableInfo.COLUMN_ADD_PRODUCT_LABEL_COLOR_VALUE} INTEGER, " +
            "${TableInfo.COLUMN_ADD_PRODUCT_LINE_COLOR_VALUE} INTEGER, " +
            "${TableInfo.COLUMN_ADD_PRODUCT_HINT_COLOR_VALUE} TEXT, " +
            "${TableInfo.COLUMN_COLOR_SET_ID} INTEGER" +
            ")"

    const val CREATE_TABLE_COLOR_SETS = "CREATE TABLE ${TableInfo.TABLE_NAME_COLOR_SETS} " +
            "(" +
            "${TableInfo.COLUMN_ID} INTEGER PRIMARY KEY, " +
            "${TableInfo.COLUMN_NAME} TEXT NOT NULL, " +
            "${TableInfo.COLUMN_PRIMARY_COLOR_VALUE} INTEGER, " +
            "${TableInfo.COLUMN_SECONDARY_COLOR_VALUE} INTEGER" +
            ")"

    const val DELETE_TABLE = "DROP TABLE "

    const val GET_ALL_PRODUCTS = "SELECT * FROM ${TableInfo.TABLE_NAME_PRODUCTS} ORDER BY ${TableInfo.COLUMN_PRIORITY}"

    const val GET_ALL_THEMES = "SELECT * FROM ${TableInfo.TABLE_NAME_THEMES}"

    const val GET_THEME = "SELECT * FROM ${TableInfo.TABLE_NAME_THEMES} WHERE ${TableInfo.COLUMN_ID} = "
}

class DBHelper(context: Context) : SQLiteOpenHelper(context, TableInfo.DATABASE_NAME, null, TableInfo.DATABASE_VERSION) {

    private val resources = context.resources

    companion object {
        private var instance: DBHelper? = null

        fun getInstance(context: Context? = null): DBHelper? {
            if (instance == null && context != null) {
                instance = DBHelper(context.applicationContext)
            }
            return instance
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(BasicSQLCommands.CREATE_TABLE_PRODUCTS)
        db?.execSQL(BasicSQLCommands.CREATE_TABLE_COLOR_SETS)
        db?.execSQL(BasicSQLCommands.CREATE_TABLE_THEMES)
        db?.let { createThemes(it) }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(BasicSQLCommands.DELETE_TABLE + TableInfo.TABLE_NAME_PRODUCTS)
        db?.execSQL(BasicSQLCommands.DELETE_TABLE + TableInfo.TABLE_NAME_THEMES)
        db?.execSQL(BasicSQLCommands.DELETE_TABLE + TableInfo.TABLE_NAME_COLOR_SETS)
        onCreate(db)
    }

    fun addProduct(name: String, quantity: String, priority: Int) {
        val product = ContentValues()
        product.put(TableInfo.COLUMN_NAME, name)
        product.put(TableInfo.COLUMN_QUANTITY, quantity)
        product.put(TableInfo.COLUMN_PRIORITY, priority)

        val db = this.writableDatabase
        db.insert(TableInfo.TABLE_NAME_PRODUCTS, null, product)
        db.close()
    }

    fun deleteProduct(id: Int) {
        delete(TableInfo.TABLE_NAME_PRODUCTS, id)
    }

    fun deleteTheme(id: Int) {
        delete(TableInfo.TABLE_NAME_THEMES, id)
    }

    fun editProduct(id: Int, name: String, quantity: String, priority: Int) {
        val db = this.writableDatabase
        val values = contentValuesOf()
        values.put(TableInfo.COLUMN_NAME, name)
        values.put(TableInfo.COLUMN_QUANTITY, quantity)
        values.put(TableInfo.COLUMN_PRIORITY, priority)
        db.update(TableInfo.TABLE_NAME_PRODUCTS, values, TableInfo.COLUMN_ID + "=" + id, null)
        db.close()
    }

    fun getAllProducts(): List<Product> {
        val db = this.readableDatabase
        val cursor = db.rawQuery(BasicSQLCommands.GET_ALL_PRODUCTS, null)
        val products = mutableListOf<Product>()

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getIntOrNull(cursor.getColumnIndex(TableInfo.COLUMN_ID))
                val name = cursor.getStringOrNull(cursor.getColumnIndex(TableInfo.COLUMN_NAME))
                val quantity = cursor.getStringOrNull(cursor.getColumnIndex(TableInfo.COLUMN_QUANTITY))
                val priority = cursor.getIntOrNull(cursor.getColumnIndex(TableInfo.COLUMN_PRIORITY))

                if (id != null && name != null && quantity != null && priority != null) {
                    val product = Product(id, name, quantity, priority)
                    products.add(product)
                } else {
                    Log.e("DBHelper - getAllProducts()", "Getting product did not succeed")
                }
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return products
    }

    fun getAllThemes(): List<Theme> {
        val db = this.readableDatabase
        val cursor = db.rawQuery(BasicSQLCommands.GET_ALL_THEMES, null)
        val themes = mutableListOf<Theme>()

        if (cursor.moveToFirst()) {
            do {
                val theme = parseTheme(cursor)
                theme?.let { themes.add(it) }
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return themes
    }

    fun getTheme(themeId: Int): Theme? {
        val db = this.readableDatabase
        val cursor = db.rawQuery(BasicSQLCommands.GET_THEME + themeId, null)

        return if (cursor.moveToFirst()) {
            val theme = parseTheme(cursor)
            cursor.close()
            db.close()
            theme

        } else return null
    }

    fun saveTheme(
        name: String,
        builtInTheme: Boolean,
        listBackgroundImagePortrait: ByteArray?,
        listBackgroundImageLandscape: ByteArray?,
        addProductBackgroundImagePortrait: ByteArray?,
        addProductBackgroundImageLandscape: ByteArray?,
        listBackgroundColorPortrait: Int?,
        listBackgroundColorLandscape: Int?,
        addProductBackgroundColorPortrait: Int?,
        addProductBackgroundColorLandscape: Int?,
        productItemBackgroundValue: String,
        productItemTextColorValue: Int,
        deleteIconColorValue: Int,
        deleteIcon: Icon,
        boldProductName: Boolean,
        addProductTextColorValue: Int,
        addProductLabelColorValue: Int,
        addProductLineColorValue: Int,
        addProductHintColorValue: String,
        colorSetId: Int,
        database: SQLiteDatabase? = null,
        id: Int? = null
    ) {
        val theme = ContentValues()

        id?.let{ theme.put(TableInfo.COLUMN_ID, it) }
        theme.put(TableInfo.COLUMN_NAME, name)
        theme.put(TableInfo.COLUMN_BUILT_IN_THEME, builtInTheme)
        theme.put(TableInfo.COLUMN_LIST_BACKGROUND_IMAGE_PORTRAIT, listBackgroundImagePortrait)
        theme.put(TableInfo.COLUMN_LIST_BACKGROUND_IMAGE_LANDSCAPE, listBackgroundImageLandscape)
        theme.put(TableInfo.COLUMN_ADD_PRODUCT_BACKGROUND_IMAGE_PORTRAIT, addProductBackgroundImagePortrait)
        theme.put(TableInfo.COLUMN_ADD_PRODUCT_BACKGROUND_IMAGE_LANDSCAPE, addProductBackgroundImageLandscape)
        theme.put(TableInfo.COLUMN_LIST_BACKGROUND_COLOR_PORTRAIT, listBackgroundColorPortrait)
        theme.put(TableInfo.COLUMN_LIST_BACKGROUND_COLOR_LANDSCAPE, listBackgroundColorLandscape)
        theme.put(TableInfo.COLUMN_ADD_PRODUCT_BACKGROUND_COLOR_PORTRAIT, addProductBackgroundColorPortrait)
        theme.put(TableInfo.COLUMN_ADD_PRODUCT_BACKGROUND_COLOR_LANDSCAPE, addProductBackgroundColorLandscape)

        theme.put(TableInfo.COLUMN_PRODUCT_ITEM_BACKGROUND_VALUE, productItemBackgroundValue)
        theme.put(TableInfo.COLUMN_PRODUCT_ITEM_TEXT_COLOR_VALUE, productItemTextColorValue)
        theme.put(TableInfo.COLUMN_DELETE_ICON_COLOR_VALUE, deleteIconColorValue)
        theme.put(TableInfo.COLUMN_DELETE_ICON, deleteIcon.iconId)
        theme.put(TableInfo.COLUMN_BOLD_PRODUCT_NAME, boldProductName)

        theme.put(TableInfo.COLUMN_ADD_PRODUCT_TEXT_COLOR_VALUE, addProductTextColorValue)
        theme.put(TableInfo.COLUMN_ADD_PRODUCT_LABEL_COLOR_VALUE, addProductLabelColorValue)
        theme.put(TableInfo.COLUMN_ADD_PRODUCT_LINE_COLOR_VALUE, addProductLineColorValue)
        theme.put(TableInfo.COLUMN_ADD_PRODUCT_HINT_COLOR_VALUE, addProductHintColorValue)

        theme.put(TableInfo.COLUMN_COLOR_SET_ID, colorSetId)

        val db = database ?: this.writableDatabase
        db.insert(TableInfo.TABLE_NAME_THEMES, null, theme)

        if (database == null) {
            db.close()
        }
    }

    private fun createColorSets(database: SQLiteDatabase) {
        saveColorSet(
            ThemeConstants.COLOR_SET_SEA_ID,
            resources.getString(R.string.color_set_sea),
            resources.getColor(R.color.sea_blue_dark, null),
            resources.getColor(R.color.sea_blue_light, null),
            database
        )

        saveColorSet(
            ThemeConstants.COLOR_SET_MAGNOLIA_ID,
            resources.getString(R.string.color_set_magnolia),
            resources.getColor(R.color.magnolia_primary, null),
            resources.getColor(R.color.magnolia_secondary, null),
            database
        )

        saveColorSet(
            ThemeConstants.COLOR_SET_FRESH_ID,
            resources.getString(R.string.color_set_fresh),
            resources.getColor(R.color.fresh_primary, null),
            resources.getColor(R.color.fresh_secondary, null),
            database
        )
    }

    @SuppressLint("ResourceType")
    private fun createThemes(database: SQLiteDatabase) {
        createColorSets(database)
        val productItemBackgroundValue = resources.getString(R.color.transparent_black, null)
        val productItemTextColorValue = Color.WHITE
        val deleteIconColorValue = resources.getColor(R.color.sea_blue_light, null)
        val addProductTextColorValue = Color.WHITE
        val addProductLabelColorValue = Color.WHITE
        val addProductLineColorValue = Color.WHITE
        val addProductHintColorValue = resources.getString(R.color.transparent_white, null)


        saveTheme(
            resources.getString(R.string.theme_grocery),
            true,
            getImageAsByteArray(R.drawable.theme_grocery_list_portrait),
            getImageAsByteArray(R.drawable.theme_grocery_list_landscape),
            getImageAsByteArray(R.drawable.theme_grocery_add_product_portrait),
            getImageAsByteArray(R.drawable.theme_grocery_add_product_landscape),
            null,
            null,
            null,
            null,
            productItemBackgroundValue,
            productItemTextColorValue,
            deleteIconColorValue,
            Icon.TRASH_BIN,
            true,
            addProductTextColorValue,
            addProductLabelColorValue,
            addProductLineColorValue,
            addProductHintColorValue,
            ThemeConstants.COLOR_SET_SEA_ID,
            database,
            ThemeConstants.THEME_GROCERY_ID
        )

        saveTheme(
            resources.getString(R.string.theme_marketplace),
            true,
            getImageAsByteArray(R.drawable.theme_marketplace_list_portrait),
            getImageAsByteArray(R.drawable.theme_marketplace_list_landscape),
            getImageAsByteArray(R.drawable.theme_marketplace_add_product_portrait),
            getImageAsByteArray(R.drawable.theme_marketplace_add_product_landscape),
            null,
            null,
            null,
            null,
            productItemBackgroundValue,
            productItemTextColorValue,
            deleteIconColorValue,
            Icon.TRASH_BIN,
            true,
            addProductTextColorValue,
            addProductLabelColorValue,
            addProductLineColorValue,
            addProductHintColorValue,
            ThemeConstants.COLOR_SET_SEA_ID,
            database,
            ThemeConstants.THEME_MARKETPLACE_ID
        )

        saveTheme(
            resources.getString(R.string.theme_fashion),
            true,
            getImageAsByteArray(R.drawable.theme_fashion_list_portrait),
            getImageAsByteArray(R.drawable.theme_fashion_list_landscape),
            getImageAsByteArray(R.drawable.theme_fashion_add_product_portrait),
            getImageAsByteArray(R.drawable.theme_fashion_add_product_landscape),
            null,
            null,
            null,
            null,
            productItemBackgroundValue,
            productItemTextColorValue,
            deleteIconColorValue,
            Icon.TRASH_BIN,
            true,
            addProductTextColorValue,
            addProductLabelColorValue,
            addProductLineColorValue,
            addProductHintColorValue,
            ThemeConstants.COLOR_SET_SEA_ID,
            database,
            ThemeConstants.THEME_FASHION_ID
        )

        saveTheme(
            resources.getString(R.string.theme_christmas),
            true,
            getImageAsByteArray(R.drawable.theme_christmas_list_portrait),
            getImageAsByteArray(R.drawable.theme_christmas_list_landscape),
            getImageAsByteArray(R.drawable.theme_christmas_add_product_portrait),
            getImageAsByteArray(R.drawable.theme_christmas_add_product_landscape),
            null,
            null,
            null,
            null,
            productItemBackgroundValue,
            productItemTextColorValue,
            deleteIconColorValue,
            Icon.TRASH_BIN,
            true,
            addProductTextColorValue,
            addProductLabelColorValue,
            addProductLineColorValue,
            addProductHintColorValue,
            ThemeConstants.COLOR_SET_SEA_ID,
            database,
            ThemeConstants.THEME_CHRISTMAS_ID
        )
    }

    private fun delete(tableName: String, id: Int) {
        val db = this.writableDatabase
        db.delete(tableName, TableInfo.COLUMN_ID + "=" + id, null)
        db.close()
    }

    private fun getImageAsByteArray(imageId: Int): ByteArray {
        val drawable = ResourcesCompat.getDrawable(resources, imageId, null)
        return ImageUtils.getImageAsByteArray(drawable)
    }

    private fun parseTheme(cursor: Cursor): Theme? {
        val id = cursor.getIntOrNull(cursor.getColumnIndex(TableInfo.COLUMN_ID))
        val name = cursor.getStringOrNull(cursor.getColumnIndex(TableInfo.COLUMN_NAME))
        val builtInTheme = cursor.getIntOrNull(cursor.getColumnIndex(TableInfo.COLUMN_BUILT_IN_THEME)) != 0

        val listBackgroundImagePortrait = cursor.getBlobOrNull(cursor.getColumnIndex(TableInfo.COLUMN_LIST_BACKGROUND_IMAGE_PORTRAIT))
        val listBackgroundImageLandscape = cursor.getBlobOrNull(cursor.getColumnIndex(TableInfo.COLUMN_LIST_BACKGROUND_IMAGE_LANDSCAPE))
        val addProductBackgroundImagePortrait = cursor.getBlobOrNull(cursor.getColumnIndex(TableInfo.COLUMN_ADD_PRODUCT_BACKGROUND_IMAGE_PORTRAIT))
        val addProductBackgroundImageLandscape = cursor.getBlobOrNull(cursor.getColumnIndex(TableInfo.COLUMN_ADD_PRODUCT_BACKGROUND_IMAGE_LANDSCAPE))

        val listBackgroundColorPortrait = cursor.getIntOrNull(cursor.getColumnIndex(TableInfo.COLUMN_LIST_BACKGROUND_COLOR_PORTRAIT))
        val listBackgroundColorLandscape = cursor.getIntOrNull(cursor.getColumnIndex(TableInfo.COLUMN_LIST_BACKGROUND_COLOR_LANDSCAPE))
        val addProductBackgroundColorPortrait = cursor.getIntOrNull(cursor.getColumnIndex(TableInfo.COLUMN_ADD_PRODUCT_BACKGROUND_COLOR_PORTRAIT))
        val addProductBackgroundColorLandscape = cursor.getIntOrNull(cursor.getColumnIndex(TableInfo.COLUMN_ADD_PRODUCT_BACKGROUND_COLOR_LANDSCAPE))

        val productItemBackgroundValue = cursor.getStringOrNull(cursor.getColumnIndex(TableInfo.COLUMN_PRODUCT_ITEM_BACKGROUND_VALUE))
        val productItemTextColorValue = cursor.getIntOrNull(cursor.getColumnIndex(TableInfo.COLUMN_PRODUCT_ITEM_TEXT_COLOR_VALUE))
        val deleteIconColorValue = cursor.getIntOrNull(cursor.getColumnIndex(TableInfo.COLUMN_DELETE_ICON_COLOR_VALUE))
        val deleteIcon = cursor.getIntOrNull(cursor.getColumnIndex(TableInfo.COLUMN_DELETE_ICON))
        val boldProductName = cursor.getIntOrNull(cursor.getColumnIndex(TableInfo.COLUMN_BOLD_PRODUCT_NAME))

        val addProductTextColorValue = cursor.getIntOrNull(cursor.getColumnIndex(TableInfo.COLUMN_ADD_PRODUCT_TEXT_COLOR_VALUE))
        val addProductLabelColorValue = cursor.getIntOrNull(cursor.getColumnIndex(TableInfo.COLUMN_ADD_PRODUCT_LABEL_COLOR_VALUE))
        val addProductLineColorValue = cursor.getIntOrNull(cursor.getColumnIndex(TableInfo.COLUMN_ADD_PRODUCT_LINE_COLOR_VALUE))
        val addProductHintColorValue = cursor.getStringOrNull(cursor.getColumnIndex(TableInfo.COLUMN_ADD_PRODUCT_HINT_COLOR_VALUE))

        val colorSetId = cursor.getIntOrNull(cursor.getColumnIndex(TableInfo.COLUMN_COLOR_SET_ID))


        if (id != null && name != null
            && productItemBackgroundValue != null && productItemTextColorValue != null && deleteIconColorValue != null && deleteIcon != null
            && addProductTextColorValue != null && addProductLabelColorValue != null && addProductLineColorValue != null && addProductHintColorValue != null
            && colorSetId != null
        ) {
            return Theme(
                id,
                name,
                builtInTheme,
                listBackgroundImagePortrait,
                listBackgroundImageLandscape,
                addProductBackgroundImagePortrait,
                addProductBackgroundImageLandscape,
                listBackgroundColorPortrait,
                listBackgroundColorLandscape,
                addProductBackgroundColorPortrait,
                addProductBackgroundColorLandscape,
                productItemBackgroundValue,
                productItemTextColorValue,
                deleteIconColorValue,
                Icon.getByIconId(deleteIcon)!!,
                boldProductName != 0,
                addProductTextColorValue,
                addProductLabelColorValue,
                addProductLineColorValue,
                addProductHintColorValue,
                colorSetId
            )
        } else {
            Log.e("DBHelper - parseTheme()", "Theme parsing did not succeed")
            return null
        }
    }

    private fun saveColorSet(id: Int, name: String, primaryColorValue: Int, secondaryColorValue: Int, database: SQLiteDatabase) {
        val colorSet = ContentValues()

        colorSet.put(TableInfo.COLUMN_ID, id)
        colorSet.put(TableInfo.COLUMN_NAME, name)
        colorSet.put(TableInfo.COLUMN_PRIMARY_COLOR_VALUE, primaryColorValue)
        colorSet.put(TableInfo.COLUMN_SECONDARY_COLOR_VALUE, secondaryColorValue)

        database.insert(TableInfo.TABLE_NAME_COLOR_SETS, null, colorSet)
    }

}
