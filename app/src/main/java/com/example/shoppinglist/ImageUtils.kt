package com.example.shoppinglist

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable

object ImageUtils {

    fun getImageAsBitmap(byteArray: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }

    fun getImageAsDrawable(context: Context, byteArray: ByteArray): BitmapDrawable {
        val bitmap = getImageAsBitmap(byteArray)
        return BitmapDrawable(context.resources, bitmap)
    }
}