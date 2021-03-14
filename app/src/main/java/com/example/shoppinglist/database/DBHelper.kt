package com.example.shoppinglist.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.shoppinglist.model.Product

object TableInfo {
    const val DATABASE_NAME = "shopping_list.db"
    const val DATABASE_VERSION = 1
    const val TABLE_NAME = "shopping_list"
    const val COLUMN_ID = "id"
    const val COLUMN_NAME = "name"
    const val COLUMN_AMOUNT = "amount"
    const val COLUMN_PRIORITY = "priority"
}

object BasicSQLCommands {
    const val CREATE_TABLE =
        "CREATE TABLE ${TableInfo.TABLE_NAME} (${TableInfo.COLUMN_ID} INTEGER PRIMARY KEY, ${TableInfo.COLUMN_NAME} TEXT NOT NULL, ${TableInfo.COLUMN_AMOUNT} TEXT, ${TableInfo.COLUMN_PRIORITY} INT NOT NULL)"
    const val DELETE_TABLE = "DROP TABLE IF EXISTS ${TableInfo.TABLE_NAME}"
    const val GET_ALL_PRODUCTS =
        "SELECT * FROM ${TableInfo.TABLE_NAME} ORDER BY ${TableInfo.COLUMN_PRIORITY}"
}

class DBHelper(context: Context) :
    SQLiteOpenHelper(context, TableInfo.DATABASE_NAME, null, TableInfo.DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(BasicSQLCommands.CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(BasicSQLCommands.DELETE_TABLE)
        onCreate(db)
    }

    fun getAllProducts(): List<Product> {
        val db = this.readableDatabase
        val cursor = db.rawQuery(BasicSQLCommands.GET_ALL_PRODUCTS, null)
        val products = mutableListOf<Product>()

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(TableInfo.COLUMN_ID))
                val name = cursor.getString(cursor.getColumnIndex(TableInfo.COLUMN_NAME))
                val amount = cursor.getString(cursor.getColumnIndex(TableInfo.COLUMN_AMOUNT))
                val priority = cursor.getInt(cursor.getColumnIndex(TableInfo.COLUMN_PRIORITY))

                val product = Product(id, name, amount, priority)
                products.add(product)
            } while (cursor.moveToNext())
        }

        db.close()
        return products
    }

}
