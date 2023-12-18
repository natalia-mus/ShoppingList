package com.example.shoppinglist

import android.graphics.Bitmap
import android.graphics.BitmapFactory

object ImageUtils {

    fun getImageAsBitmap(byteArray: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }
}