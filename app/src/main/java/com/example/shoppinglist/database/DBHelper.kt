package com.example.shoppinglist.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.core.content.contentValuesOf
import com.example.shoppinglist.R
import com.example.shoppinglist.model.Product
import com.example.shoppinglist.model.Theme

object TableInfo {
    const val DATABASE_NAME = "shopping_list.db"
    const val DATABASE_VERSION = 5
    const val TABLE_NAME_PRODUCTS = "shopping_list"
    const val TABLE_NAME_THEMES = "themes"
    const val COLUMN_ID = "id"
    const val COLUMN_NAME = "name"
    const val COLUMN_QUANTITY = "quantity"
    const val COLUMN_PRIORITY = "priority"
    const val COLUMN_LIST_BACKGROUND = "listBackground"
    const val COLUMN_ADD_PRODUCT_BACKGROUND = "addProductBackground"
}

object BasicSQLCommands {

    const val CREATE_TABLE_PRODUCTS = "CREATE TABLE ${TableInfo.TABLE_NAME_PRODUCTS} (${TableInfo.COLUMN_ID} INTEGER PRIMARY KEY, ${TableInfo.COLUMN_NAME} TEXT NOT NULL, ${TableInfo.COLUMN_QUANTITY} TEXT, ${TableInfo.COLUMN_PRIORITY} INT NOT NULL)"

    const val CREATE_TABLE_THEMES = "CREATE TABLE ${TableInfo.TABLE_NAME_THEMES} (${TableInfo.COLUMN_ID} INTEGER PRIMARY KEY, ${TableInfo.COLUMN_NAME} TEXT NOT NULL, ${TableInfo.COLUMN_LIST_BACKGROUND} TEXT, ${TableInfo.COLUMN_ADD_PRODUCT_BACKGROUND} TEXT)"

    const val DELETE_TABLE = "SELECT * FROM "

    const val GET_ALL_PRODUCTS = "SELECT * FROM ${TableInfo.TABLE_NAME_PRODUCTS} ORDER BY ${TableInfo.COLUMN_PRIORITY}"

    const val GET_ALL_THEMES = "SELECT * FROM ${TableInfo.TABLE_NAME_THEMES}"
}

class DBHelper(context: Context) :
    SQLiteOpenHelper(context, TableInfo.DATABASE_NAME, null, TableInfo.DATABASE_VERSION) {

    private val resources = context.resources

    companion object {
        var instance: DBHelper? = null

        fun getInstance(context: Context): DBHelper {
            if (instance == null) {
                instance = DBHelper(context.applicationContext)
            }
            return instance as DBHelper
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
                val id = cursor.getInt(cursor.getColumnIndex(TableInfo.COLUMN_ID))
                val name = cursor.getString(cursor.getColumnIndex(TableInfo.COLUMN_NAME))
                val listBackground = cursor.getString(cursor.getColumnIndex(TableInfo.COLUMN_LIST_BACKGROUND))
                val addProductBackground = cursor.getString(cursor.getColumnIndex(TableInfo.COLUMN_ADD_PRODUCT_BACKGROUND))

                val theme = Theme(id, name, listBackground, addProductBackground)
                themes.add(theme)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return themes
    }

    fun saveTheme(name: String, listBackground: String, addProductBackground: String, database: SQLiteDatabase?) {
        val theme = ContentValues()

        theme.put(TableInfo.COLUMN_NAME, name)
        theme.put(TableInfo.COLUMN_LIST_BACKGROUND, listBackground)
        theme.put(TableInfo.COLUMN_ADD_PRODUCT_BACKGROUND, addProductBackground)

        val db = database ?: this.writableDatabase
        db.insert(TableInfo.TABLE_NAME_THEMES, null, theme)
    }

    private fun createThemes(database: SQLiteDatabase) {
        saveTheme(resources.getString(R.string.theme_grocery), Theme.THEME_GROCERY_LIST, Theme.THEME_GROCERY_ADD_PRODUCT, database)
        saveTheme(resources.getString(R.string.theme_marketplace), Theme.THEME_MARKETPLACE_LIST, Theme.THEME_MARKETPLACE_ADD_PRODUCT, database)
        saveTheme(resources.getString(R.string.theme_fashion), Theme.THEME_FASHION_LIST, Theme.THEME_FASHION_ADD_PRODUCT, database)
        saveTheme(resources.getString(R.string.theme_christmas), Theme.THEME_CHRISTMAS_LIST, Theme.THEME_CHRISTMAS_ADD_PRODUCT, database)
    }

    private fun delete(tableName: String, id: Int) {
        val db = this.writableDatabase
        db.delete(tableName, TableInfo.COLUMN_ID + "=" + id, null)
        db.close()
    }

}
