package com.example.shoppinglist.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.core.content.contentValuesOf
import androidx.core.content.res.ResourcesCompat
import androidx.core.database.getIntOrNull
import com.example.shoppinglist.ImageUtils
import com.example.shoppinglist.R
import com.example.shoppinglist.model.Icon
import com.example.shoppinglist.model.Product
import com.example.shoppinglist.model.Theme

object TableInfo {
    const val DATABASE_NAME = "shopping_list.db"
    const val DATABASE_VERSION = 8
    const val TABLE_NAME_PRODUCTS = "shopping_list"
    const val TABLE_NAME_THEMES = "themes"
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
    const val COLUM_DELETE_ICON_COLOR_VALUE = "deleteIconColorValue"
    const val COLUMN_DELETE_ICON = "deleteIcon"
    const val COLUMN_BOLD_PRODUCT_NAME = "boldProductName"
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
            "${TableInfo.COLUMN_ADD_PRODUCT_BACKGROUND_COLOR_LANDSCAPE} INTEGER" +
            "${TableInfo.COLUMN_PRODUCT_ITEM_BACKGROUND_VALUE} TEXT" +
            "${TableInfo.COLUMN_PRODUCT_ITEM_TEXT_COLOR_VALUE} INTEGER" +
            "${TableInfo.COLUM_DELETE_ICON_COLOR_VALUE} INTEGER" +
            "${TableInfo.COLUMN_DELETE_ICON} INTEGER" +
            "${TableInfo.COLUMN_BOLD_PRODUCT_NAME} INTEGER" +
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
        db?.execSQL(BasicSQLCommands.CREATE_TABLE_THEMES)
        db?.let { createThemes(it) }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(BasicSQLCommands.DELETE_TABLE + TableInfo.TABLE_NAME_PRODUCTS)
        db?.execSQL(BasicSQLCommands.DELETE_TABLE + TableInfo.TABLE_NAME_THEMES)
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
                val id = cursor.getInt(cursor.getColumnIndex(TableInfo.COLUMN_ID))
                val name = cursor.getString(cursor.getColumnIndex(TableInfo.COLUMN_NAME))
                val quantity = cursor.getString(cursor.getColumnIndex(TableInfo.COLUMN_QUANTITY))
                val priority = cursor.getInt(cursor.getColumnIndex(TableInfo.COLUMN_PRIORITY))

                val product = Product(id, name, quantity, priority)
                products.add(product)
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
                themes.add(theme)
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
        database: SQLiteDatabase? = null
    ) {
        val theme = ContentValues()

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

        val db = database ?: this.writableDatabase
        db.insert(TableInfo.TABLE_NAME_THEMES, null, theme)

        if (database == null) {
            db.close()
        }
    }

    private fun createThemes(database: SQLiteDatabase) {
        saveTheme(resources.getString(R.string.theme_grocery),
            true,
            getImageAsByteArray(R.drawable.theme_grocery_list_portrait),
            getImageAsByteArray(R.drawable.theme_grocery_list_landscape),
            getImageAsByteArray(R.drawable.theme_grocery_add_product_portrait),
            getImageAsByteArray(R.drawable.theme_grocery_add_product_landscape),
            null,
            null,
            null,
            null,
            database)

        saveTheme(resources.getString(R.string.theme_marketplace),
            true,
            getImageAsByteArray(R.drawable.theme_marketplace_list_portrait),
            getImageAsByteArray(R.drawable.theme_marketplace_list_landscape),
            getImageAsByteArray(R.drawable.theme_marketplace_add_product_portrait),
            getImageAsByteArray(R.drawable.theme_marketplace_add_product_landscape),
            null,
            null,
            null,
            null,
            database)

        saveTheme(resources.getString(R.string.theme_fashion),
            true,
            getImageAsByteArray(R.drawable.theme_fashion_list_portrait),
            getImageAsByteArray(R.drawable.theme_fashion_list_landscape),
            getImageAsByteArray(R.drawable.theme_fashion_add_product_portrait),
            getImageAsByteArray(R.drawable.theme_fashion_add_product_landscape),
            null,
            null,
            null,
            null,
            database)

        saveTheme(resources.getString(R.string.theme_christmas),
            true,
            getImageAsByteArray(R.drawable.theme_christmas_list_portrait),
            getImageAsByteArray(R.drawable.theme_christmas_list_landscape),
            getImageAsByteArray(R.drawable.theme_christmas_add_product_portrait),
            getImageAsByteArray(R.drawable.theme_christmas_add_product_landscape),
            null,
            null,
            null,
            null,
            database)
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

    private fun parseTheme(cursor: Cursor): Theme {
        val id = cursor.getInt(cursor.getColumnIndex(TableInfo.COLUMN_ID))
        val name = cursor.getString(cursor.getColumnIndex(TableInfo.COLUMN_NAME))
        val builtInTheme = cursor.getInt(cursor.getColumnIndex(TableInfo.COLUMN_BUILT_IN_THEME)) != 0

        val listBackgroundImagePortrait = cursor.getBlob(cursor.getColumnIndex(TableInfo.COLUMN_LIST_BACKGROUND_IMAGE_PORTRAIT))
        val listBackgroundImageLandscape = cursor.getBlob(cursor.getColumnIndex(TableInfo.COLUMN_LIST_BACKGROUND_IMAGE_LANDSCAPE))
        val addProductBackgroundImagePortrait = cursor.getBlob(cursor.getColumnIndex(TableInfo.COLUMN_ADD_PRODUCT_BACKGROUND_IMAGE_PORTRAIT))
        val addProductBackgroundImageLandscape = cursor.getBlob(cursor.getColumnIndex(TableInfo.COLUMN_ADD_PRODUCT_BACKGROUND_IMAGE_LANDSCAPE))

        val listBackgroundColorPortrait = cursor.getIntOrNull(cursor.getColumnIndex(TableInfo.COLUMN_LIST_BACKGROUND_COLOR_PORTRAIT))
        val listBackgroundColorLandscape = cursor.getIntOrNull(cursor.getColumnIndex(TableInfo.COLUMN_LIST_BACKGROUND_COLOR_LANDSCAPE))
        val addProductBackgroundColorPortrait = cursor.getIntOrNull(cursor.getColumnIndex(TableInfo.COLUMN_ADD_PRODUCT_BACKGROUND_COLOR_PORTRAIT))
        val addProductBackgroundColorLandscape = cursor.getIntOrNull(cursor.getColumnIndex(TableInfo.COLUMN_ADD_PRODUCT_BACKGROUND_COLOR_LANDSCAPE))

        val productItemBackgroundValue = cursor.getString(cursor.getColumnIndex(TableInfo.COLUMN_PRODUCT_ITEM_BACKGROUND_VALUE))
        val productItemTextColorValue = cursor.getInt(cursor.getColumnIndex(TableInfo.COLUMN_PRODUCT_ITEM_TEXT_COLOR_VALUE))
        val deleteIconColorValue = cursor.getInt(cursor.getColumnIndex(TableInfo.COLUM_DELETE_ICON_COLOR_VALUE))
        val deleteIcon = cursor.getInt(cursor.getColumnIndex(TableInfo.COLUMN_DELETE_ICON))
        val boldProductName = cursor.getInt(cursor.getColumnIndex(TableInfo.COLUMN_BOLD_PRODUCT_NAME))


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
            boldProductName != 0
        )
    }

}
